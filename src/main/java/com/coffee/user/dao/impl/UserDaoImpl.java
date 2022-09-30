package com.coffee.user.dao.impl;

import com.coffee.user.dao.UserDao;
import com.coffee.user.dao.mapper.UserMapper;
import com.coffee.user.domain.po.UserPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    private UserMapper userMapper;

    @Override
    public boolean create(UserPO user) {
        return userMapper.create(user) > 0 ? Boolean.TRUE : Boolean.FALSE;
    }

    @Override
    public boolean delete(String id) {
        return userMapper.delete(id) > 0 ? Boolean.TRUE : Boolean.FALSE;
    }

    @Override
    public boolean update(UserPO user) {
        return userMapper.update(user) > 0 ? Boolean.TRUE : Boolean.FALSE;
    }

    @Override
    public UserPO findById(String id) {
        return userMapper.findOne(id);
    }

    @Override
    public UserPO findByUserName(String userName) {
        return userMapper.findByUserName(userName);
    }
}
