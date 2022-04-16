package com.test.backend.java.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.test.backend.java.entity.User;
import com.test.backend.java.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Api(tags="用户管理")
@RequestMapping("/user")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * RESTful get
     *
     * http://localhost:8080/user/get/U001
     *
     * @param userId
     * @return
     */
    @ApiOperation("get user")
    @GetMapping("/get/{userId}")
    public User get(@PathVariable String userId) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUserId, userId);
        return userService.getOne(wrapper);
    }

    /**
     * RESTful create
     *
     * http://localhost:8080/user/create
     *
     * @param user
     * @return
     */
    @ApiOperation("create user")
    @PostMapping("/create")
    public boolean create(@RequestBody User user) {
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        boolean save = userService.save(user);
        log.info("create user: {} success", JSONObject.toJSONString(user));
        return save;
    }

    /**
     * RESTful update
     *
     * http://localhost:8080/user/update
     *
     * @param user
     * @return
     */
    @ApiOperation("update user")
    @PutMapping("/update")
    public boolean update(@RequestBody User user) {
        LambdaUpdateWrapper<User> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(User::getUserId, user.getUserId());
        user.setUpdatedAt(LocalDateTime.now());
        boolean update = userService.update(user, wrapper);
        log.info("update user: {} success", JSONObject.toJSONString(user));
        return update;
    }

    /**
     * RESTful delete
     *
     * http://localhost:8080/user/delete
     *
     * @param user
     * @return
     */
    @ApiOperation("delete user")
    @DeleteMapping("/delete")
    public boolean delete(@RequestBody User user) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUserId, user.getUserId());
        boolean remove = userService.remove(wrapper);
        log.info("delete user: {} success", JSONObject.toJSONString(user));
        return remove;
    }

    /**
     * RESTful get
     *
     * http://localhost:8080/user/getFriends/U001
     *
     * @param userId
     * @return
     */
    @ApiOperation("get user friends")
    @GetMapping("/getFriends/{userId}")
    public List<User> getFriends(@PathVariable String userId) {
        return userService.getUserFriends(userId);
    }

}
