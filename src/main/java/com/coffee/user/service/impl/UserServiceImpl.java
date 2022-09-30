package com.coffee.user.service.impl;

import com.coffee.user.dao.UserDao;
import com.coffee.user.domain.po.UserPO;
import com.coffee.user.exception.BizException;
import com.coffee.user.enums.ErrorCodeEnum;
import com.coffee.user.request.UserCreateParam;
import com.coffee.user.response.info.UserInfo;
import com.coffee.user.request.UserUpdateParam;
import com.coffee.user.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

import static com.coffee.user.enums.ErrorCodeEnum.ILLEGAL_ARGUMENTS;


/**
 * BizUser默认服务实现
 */
@Service
public class UserServiceImpl implements UserService {

    private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserDao userDao;

    @Override
    public UserInfo create(UserCreateParam createParam) {
        //先检查用户是否已经存在了，如果已经存在，报错
        UserPO user = userDao.findByUserName(createParam.getName());
        if (user != null) {
            throw new BizException(ILLEGAL_ARGUMENTS.getErrorCode(), "该用户名已被注册，请换个名字再试");
        }
        //创建user
        UserPO userPO = new UserPO();
        userPO.setAddress(createParam.getAddress());
        userPO.setDescription(createParam.getDescription());
        userPO.setDob(createParam.getDob());
        userPO.setName(createParam.getName());
        userPO.setPassword(createParam.getPassword());
        userDao.create(userPO);

        UserInfo userInfo = new UserInfo();
        BeanUtils.copyProperties(userPO, userInfo);
        logger.info("New user created: {}", createParam);

        return userInfo;
    }

    @Override
    public Boolean delete(String userId) {
        if (StringUtils.isBlank(userId)) {
            throw new BizException(ILLEGAL_ARGUMENTS.getErrorCode(), "用户id不能为空");
        }
        UserPO existUserPO = userDao.findById(userId);
        if (existUserPO == null) {
            throw new BizException(ILLEGAL_ARGUMENTS.getErrorCode(), "用户不存在");
        }
        //注销bizUser：修改用户状态为注销 & 逻辑删除
        userDao.delete(userId);
        return Boolean.TRUE;
    }

    /**
     * get
     *
     * @param id
     * @return
     */
    @Override
    public UserInfo findOne(String id) {
        if (StringUtils.isBlank(id)) {
            throw new BizException(ILLEGAL_ARGUMENTS.getErrorCode(), "用户id不能为空");
        }
        UserPO userPO = userDao.findById(id);

        UserInfo userInfo = new UserInfo();
        if (userPO != null) {
            BeanUtils.copyProperties(userPO, userInfo);
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Found one bizUser with ID: {}, result: {}", id, userPO);
        }

        return userInfo;
    }

    @Override
    public UserInfo findByUserName(String userName) {
        if (StringUtils.isBlank(userName)) {

        }
        UserPO userPO = userDao.findByUserName(userName);

        UserInfo userInfo = null;
        if (userPO != null) {
            BeanUtils.copyProperties(userPO, userInfo);
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Found one user result: {}", userPO);
        }

        return userInfo;
    }

    @Override
    public Boolean update(UserUpdateParam updateParam) {
        String userId = updateParam.getId();
        //查询bizUser
        UserPO existsUserPO = userDao.findById(userId);
        if (existsUserPO == null) {
            throw new BizException(ErrorCodeEnum.ENTITY_NOT_FOUND);
        }
        if (StringUtils.isNotBlank(updateParam.getAddress())) {
            existsUserPO.setAddress(updateParam.getAddress());
        }
        if (StringUtils.isNotBlank(updateParam.getDescription())) {
            existsUserPO.setDescription(updateParam.getDescription());
        }
        if (updateParam.getDob() != null) {
            existsUserPO.setDob(updateParam.getDob());
        }
        existsUserPO.setUpdatedAt(new Date());
        return userDao.update(existsUserPO);
    }
}
