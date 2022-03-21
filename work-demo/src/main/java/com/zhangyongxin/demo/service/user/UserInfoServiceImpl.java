package com.zhangyongxin.demo.service.user;

import com.zhangyongxin.demo.exception.ServiceException;
import com.zhangyongxin.demo.mapper.UserInfoMapper;
import com.zhangyongxin.demo.model.user.UserInfo;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created by dinggh on 18/6/30.
 */
@Service
@Transactional(timeout = 5, rollbackFor = Exception.class)
public class UserInfoServiceImpl implements UserInfoService {

    @Resource
    private UserInfoMapper userInfoMapper;

    @Override
    public int addOne(UserInfo userInfo) {
        try {
            userInfoMapper.insertSelective(userInfo);
        } catch (DuplicateKeyException e) {
            throw new ServiceException(String.format("username :%s already exists", userInfo.getUsername()));
        }
        return userInfo.getId();
    }

    @Override
    public boolean updateOne(UserInfo userInfo) {
        int count = 0;
        try {
            count = userInfoMapper.updateByPrimaryKeySelective(userInfo);
        } catch (DuplicateKeyException e) {
            throw new ServiceException(String.format("username :%s already exists", userInfo.getUsername()));
        }
        return count > 0;
    }

    @Override
    public UserInfo findByName(String username) {
        return userInfoMapper.findByUsername(username);
    }

    @Override
    public boolean deleteOne(int id) {
        return userInfoMapper.deleteByPrimaryKey(id) > 0;
    }
}
