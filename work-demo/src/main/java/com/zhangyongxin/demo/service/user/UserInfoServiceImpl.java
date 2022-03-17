package com.zhangyongxin.demo.service.user;

import com.zhangyongxin.demo.mapper.UserInfoMapper;
import com.zhangyongxin.demo.model.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by dinggh on 18/6/30.
 */
@Service
@Transactional(timeout = 5,rollbackFor = Exception.class)
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired(required = false)
    private UserInfoMapper userInfoMapper;
    @Override
    public int addOne(UserInfo userInfo) {
        userInfoMapper.insertSelective(userInfo);
        return userInfo.getId();
    }

    @Override
    public void updateOne(UserInfo userInfo) {
        userInfoMapper.updateByPrimaryKeySelective(userInfo);
    }

    @Override
    public UserInfo findByName(String username) {
        return userInfoMapper.findByUsername(username);
    }
}
