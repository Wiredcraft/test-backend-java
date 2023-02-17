package com.wiredcraft.testbackend.service;

import com.wiredcraft.testbackend.entity.PageResult;
import com.wiredcraft.testbackend.entity.Result;
import com.wiredcraft.testbackend.entity.User;
import com.wiredcraft.testbackend.entity.param.PageParam;

public interface UserService {

    /**
     * query user by id
     *
     * @param id
     * @return
     */
    Result<User> getUserById(Long id);

    /**
     * query user list
     *
     * @param user
     * @return
     */
    Result<PageResult<User>> getUserList(User user, PageParam pageParam);

    /**
     * create new user
     *
     * @param user
     * @return
     */
    Result<User> createUser(User user);

    /**
     * update user info
     *
     * @param id
     * @param user
     * @return
     */
    Result<User> updateUser(Long id, User user);

    /**
     * delete user by user id
     *
     * @param id
     * @return
     */
    Result<Boolean> deleteUserById(Long id);

}
