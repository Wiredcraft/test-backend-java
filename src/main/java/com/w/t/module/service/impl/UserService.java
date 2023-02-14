package com.w.t.module.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.w.t.common.core.constant.LocalStorageConstant;
import com.w.t.common.security.JwtUtil;
import com.w.t.module.entity.User;
import com.w.t.common.redis.RedisManager;
import com.w.t.module.mapper.UserMapper;
import com.w.t.module.service.IUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.w.t.module.util.PasswordUtil;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class UserService implements IUserService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    RedisManager redisManager;

    @Override
    public void registerUser(String username, String password) {
        if (StringUtils.isEmpty(username)) {
            throw new RuntimeException("the user name can't be empty.");
        }
        if (StringUtils.isEmpty(password)) {
            throw new RuntimeException("the user password can't be empty.");
        }

        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getName, username);
        int userCount = userMapper.selectCount(lambdaQueryWrapper);
        if (userCount > 0) {
            throw new RuntimeException("the user already existed.");
        }

        String encryptedPassword = PasswordUtil.encryptPassword(password, LocalStorageConstant.USER_SALT);
        User user = new User();
        user.setName(username);
        user.setPassword(encryptedPassword);
        userMapper.insert(user);
    }

    @Override
    public String login(String username, String password) {
        if (StringUtils.isEmpty(username)) {
            throw new RuntimeException("the user name can't be empty.");
        }
        if (StringUtils.isEmpty(password)) {
            throw new RuntimeException("the user password can't be empty.");
        }

        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getName, username);
        User user = userMapper.selectOne(lambdaQueryWrapper);
        if (Objects.isNull(user)) {
            throw new RuntimeException("the user's name can't be find.");
        }

        String encryptedPassword = PasswordUtil.encryptPassword(password, LocalStorageConstant.USER_SALT);
        if (!user.getPassword().equals(encryptedPassword)) {
            throw new RuntimeException("the user's password can't be matched");
        }

        String loginKey = UUID.randomUUID().toString();
        String token = JwtUtil.createToken(loginKey, user.getId());
        String cacheKey = String.valueOf(user.getId()) + ":" + user.getName();
        redisManager.setCacheMapValue(cacheKey, loginKey, token);
        return token;
    }

    @Override
    public void logout(Long userId) {
        User user = userMapper.selectById(userId);
        if (Objects.nonNull(user)) {
            String cacheKey = String.valueOf(user.getId()) + ":" + user.getName();
            redisManager.deleteObject(cacheKey);
        }
    }

    @Override
    public int createUser(User user) {
        //check the user's name valid.
        if (StringUtils.isEmpty(user.getName())) {
            throw new RuntimeException("the user name can't be empty.");
        }
        //check the user weather already existed.
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getName, user.getName());
        int userCount = userMapper.selectCount(lambdaQueryWrapper);
        if (userCount > 0) {
            throw new RuntimeException("user already existed.");
        }
        return userMapper.insert(user);
    }

    @Override
    public User getUserById(Long userId) {
        return userMapper.selectById(userId);
    }

    @Override
    public User getUserByName(String username) {
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getName, username);
        return userMapper.selectOne(lambdaQueryWrapper);
    }

    @Override
    public List<User> getAllUsers() {
        return userMapper.selectList(new QueryWrapper<>());
    }

    @Override
    public Integer updateUserById(Long userId, User user) {
        //check the user's id valid.
        if (0L == userId) {
            throw new RuntimeException("the user id is not valid.");
        }
        //query the user info by user id
        User sourceUser = userMapper.selectById(userId);
        if (Objects.isNull(user)) {
            throw new RuntimeException("the user can't be find.");
        }
        //replace update info and update db
        BeanUtils.copyProperties(user, sourceUser);
        return userMapper.updateById(sourceUser);
    }

    @Override
    public Integer removeUserById(Long userId) {
        return userMapper.deleteById(userId);
    }

}
