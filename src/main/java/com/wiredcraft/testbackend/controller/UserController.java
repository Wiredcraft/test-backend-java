package com.wiredcraft.testbackend.controller;

import com.wiredcraft.testbackend.entity.PageResult;
import com.wiredcraft.testbackend.entity.Result;
import com.wiredcraft.testbackend.entity.User;
import com.wiredcraft.testbackend.entity.param.PageParam;
import com.wiredcraft.testbackend.entity.param.UserParam;
import com.wiredcraft.testbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * user api
 */
@RestController
@RequestMapping("user")
@Validated
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * query user by id
     */
    @GetMapping("query/{userId}")
    public Result<User> getUserById(@PathVariable("userId") Long userId) {
        return userService.getUserById(userId);
    }

    /**
     * query user list
     */
    @GetMapping("query")
    public Result<PageResult<User>> getUserList(User user, PageParam pageParam) {
        return userService.getUserList(user, pageParam);
    }

    /**
     * create user
     */
    @PostMapping("create")
    public Result<User> createUser(@RequestBody @Valid UserParam userParam) {
        return userService.createUser(UserParam.convertToUser(userParam));
    }

    /**
     * update user by userId
     */
    @PutMapping("update/{userId}")
    public Result<User> updateUser(@PathVariable("userId") Long userId, UserParam userParam) {
        return userService.updateUser(userId, userParam);
    }

    /**
     * delete user by userId
     */
    @DeleteMapping("delete/{userId}")
    public Result<Boolean> deleteUserById(@PathVariable("userId") Long userId) {
        return userService.deleteUserById(userId);
    }

}
