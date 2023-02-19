package com.wiredcraft.testbackend.service;

import com.wiredcraft.testbackend.entity.PageResult;
import com.wiredcraft.testbackend.entity.Result;
import com.wiredcraft.testbackend.entity.User;
import com.wiredcraft.testbackend.entity.param.PageParam;
import com.wiredcraft.testbackend.entity.param.UserParam;

import java.util.List;

public interface UserService {

    /**
     * query user by id
     *
     * @param id
     * @return
     */
    Result<User> getUserById(Long id);

    /**
     * query user list by ids
     *
     * @param ids
     * @return
     */
    List<User> getUserByIds(List<Long> ids);

    /**
     * query user list
     *
     * @param user
     * @return
     */
    Result<PageResult<User>> getUserList(User user, PageParam pageParam);

    /**
     * query user by userName
     *
     * @param userName
     * @return
     */
    User getUserByName(String userName);

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
     * @param userParam
     * @return
     */
    Result<User> updateUser(Long id, UserParam userParam);

    /**
     * delete user by user id
     *
     * @param id
     * @return
     */
    Result<Boolean> deleteUserById(Long id);

}
