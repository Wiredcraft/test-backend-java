package com.test.demo.service.impl;

import com.test.demo.entity.UserDo;
import com.test.demo.mapper.UserMapper;
import com.test.demo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * The type User service.
 *
 * @author zhangrucheng on 2023/5/21
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public int registerUser(UserDo userDo) {
        log.info("register a new user [{}]", userDo);
        userDo.setPassword(passwordEncoder.encode(userDo.getPassword()));
        return userMapper.addUser(userDo);
    }

    @Override
    public void updateUser(UserDo userDo) {
        log.info("update user with new info [{}]", userDo);
        if (!userDo.getPassword().isEmpty()) {
            userDo.setPassword(passwordEncoder.encode(userDo.getPassword()));
        }
        userMapper.updateUser(userDo);
    }

    @Override
    public void deleteUser(int id) {
        log.info("delete user with id [{}]", id);
        userMapper.deleteUserById(id);
    }

    public UserDo getUserByName(String name) {
        log.info("query user with name [{}]", name);
        List<UserDo> userDoList = userMapper.selectUserByName(name);
        if (userDoList != null && !userDoList.isEmpty()) {
            return userDoList.get(0);
        } else {
            return null;
        }
    }

    @Override
    public Optional<UserDo> getUserById(int id) {
        log.info("query user with id [{}]", id);
        return userMapper.selectUserById(id);
    }

    @Override
    public List<UserDo> getUserByCondition(UserDo userDo) {
        log.info("query users with condition, where the condition is [{}]", userDo);
        if (!userDo.getPassword().isEmpty()) {
            userDo.setPassword(passwordEncoder.encode(userDo.getPassword()));
        }
        return userMapper.selectUserByCondition(userDo);
    }
}
