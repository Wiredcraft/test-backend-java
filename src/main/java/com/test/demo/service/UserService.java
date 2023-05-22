package com.test.demo.service;

import com.test.demo.entity.UserDo;
import org.apache.ibatis.javassist.NotFoundException;

import java.util.List;
import java.util.Optional;

/**
 * The interface User service.
 *
 * @author zhangrucheng on 2023/5/21
 */
public interface UserService {

    /**
     * Register user int.
     *
     * @param userDo the user
     * @return the int
     */
    int registerUser(UserDo userDo);

    /**
     * Update user.
     *
     * @param userDo the user
     */
    void updateUser(UserDo userDo);

    /**
     * Delete user.
     *
     * @param id the id
     */
    void deleteUser(int id);

    /**
     * Gets user by name and password.
     *
     * @param name     the name
     * @return the user by name
     */
    UserDo getUserByName(String name);

    /**
     * Gets user by id.
     *
     * @param id the id
     * @return the user by id
     */
    Optional<UserDo> getUserById(int id);

    /**
     * Gets user by condition.
     *
     * @param userDo the user
     * @return the user by condition
     */
    List<UserDo> getUserByCondition(UserDo userDo);
}
