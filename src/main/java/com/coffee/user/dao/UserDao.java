package com.coffee.user.dao;


import com.coffee.user.domain.po.UserPO;

public interface UserDao {
    boolean create(UserPO user);

    boolean delete(String id);

    boolean update(UserPO user);

    UserPO findById(String id);

    UserPO findByUserName(String userName);
}
