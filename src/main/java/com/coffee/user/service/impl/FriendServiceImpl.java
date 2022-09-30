package com.coffee.user.service.impl;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.coffee.user.dao.FriendDao;
import com.coffee.user.dao.UserDao;
import com.coffee.user.domain.po.FriendPO;
import com.coffee.user.domain.po.UserPO;
import com.coffee.user.exception.BizException;
import com.coffee.user.request.FriendCreateParam;
import com.coffee.user.response.info.FriendInfo;
import com.coffee.user.service.FriendService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.coffee.user.enums.ErrorCodeEnum.ILLEGAL_ARGUMENTS;

@Service
public class FriendServiceImpl implements FriendService {
    private static Logger logger = LoggerFactory.getLogger(FriendServiceImpl.class);

    private static final String USER_FRIENDS_CACHE_KEY = "user_friends_cache_key|";

    @Autowired
    private FriendDao friendDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 指定用户新增朋友朋友
     * @param createParam
     * @return
     */
    @Override
    @Transactional
    public boolean create(FriendCreateParam createParam) {
        if (StringUtils.isBlank(createParam.getUserId()) || StringUtils.isBlank(createParam.getFriendId())) {
            logger.warn("用户id和朋友的用户ID都不能为空 userId:{},friendId={}", createParam.getUserId(), createParam.getFriendId());
            throw new BizException(ILLEGAL_ARGUMENTS.getErrorCode(),"用户id和朋友的用户ID都不能为空");
        }
        UserPO userPO = userDao.findById(createParam.getUserId());
        if (userPO == null) {
            logger.warn("用户不存在 userId:{} ", createParam.getUserId());
            throw new BizException(ILLEGAL_ARGUMENTS.getErrorCode(),"用户不存在");
        }
        UserPO friendUserPO = userDao.findById(createParam.getFriendId());
        if (friendUserPO == null) {
            logger.warn("朋友不存在 friendId:{} ", createParam.getFriendId());
            throw new BizException(ILLEGAL_ARGUMENTS.getErrorCode(),"朋友不存在");
        }

        FriendPO friendPO = new FriendPO();
        friendPO.setFriendId(createParam.getFriendId());
        friendPO.setFriendName(friendUserPO.getName());
        friendPO.setUserId(createParam.getUserId());
        friendPO.setUserName(userPO.getName());
        friendDao.create(friendPO);

        String key = USER_FRIENDS_CACHE_KEY + createParam.getUserId();
        stringRedisTemplate.delete(key);

        return Boolean.TRUE;
    }

    /**
     * 逻辑删朋友
     * @param id
     * @return
     */
    @Override
    public boolean delete(String id) {
        FriendPO friendPO = friendDao.findById(id);
        if (friendPO == null) {
            throw new BizException(ILLEGAL_ARGUMENTS.getErrorCode(),"朋友不存在");
        }
        friendDao.delete(id);

        String key = USER_FRIENDS_CACHE_KEY + friendPO.getUserId();
        stringRedisTemplate.delete(key);

        return Boolean.TRUE;
    }

    /**
     * 查询某一具体用户的朋友信息
     * 实际具体开发可冗余前端用到的用户信息
     *
     * @param userId
     * @return
     */
    @Override
    public List<FriendInfo> findByUserId(String userId) {
        List<FriendInfo> friendInfoList = new ArrayList<>();
        //查缓存
        String key = USER_FRIENDS_CACHE_KEY + userId;
        String cacheString = stringRedisTemplate.opsForValue().get(key);
        if (StringUtils.isNotBlank(cacheString)) {
            friendInfoList = JSON.parseArray(cacheString,FriendInfo.class);
            return friendInfoList;
        }
        //缓存没有查数据库
        List<FriendPO> friends = friendDao.findByUserId(userId);
        if (!CollectionUtils.isEmpty(friends)) {
            for (FriendPO friendPO : friends) {
                FriendInfo friendInfo = new FriendInfo();
                friendInfo.setFriendId(friendPO.getFriendId());
                friendInfo.setFriendName(friendPO.getFriendName());
                friendInfoList.add(friendInfo);
            }
        }
        //存缓存,空值也存
        String friendInfoListString = JSONObject.toJSONString(friendInfoList);
        stringRedisTemplate.opsForValue().set(key, friendInfoListString, 1, TimeUnit.MINUTES);
        return friendInfoList;

    }
}
