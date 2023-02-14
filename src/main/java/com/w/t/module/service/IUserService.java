package com.w.t.module.service;

import com.w.t.module.entity.User;

import java.util.List;

public interface IUserService {

    /**
     * register a user
     * @param username
     * @param password
     */
    void registerUser(String username, String password);
    /**
     * the user login
     * @param username
     * @param password
     */
    String login(String username, String password);

    /**
     * the user logout
     * @param userId
     */
    void logout(Long userId);

    /**
     * create a user
     * @param user
     * @return
     */
    int createUser(User user);

    /**
     * get user by id
     * @param userId
     * @return
     */
    User getUserById(Long userId);

    /**
     * get user by name
     * @param username
     * @return
     */
    User getUserByName(String username);

    /**
     * get all users
     * @return
     */
    List<User> getAllUsers();

    /**
     * modify user info
     * @param userId
     * @param user
     * @return
     */
    Integer updateUserById(Long userId, User user);

    /**
     * delete a user by id
     * @param userId
     * @return
     */
    Integer removeUserById(Long userId);

}
