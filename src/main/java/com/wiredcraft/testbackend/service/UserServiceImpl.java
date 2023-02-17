package com.wiredcraft.testbackend.service;

import com.alibaba.fastjson.JSON;
import com.wiredcraft.testbackend.dao.UserRepository;
import com.wiredcraft.testbackend.entity.PageResult;
import com.wiredcraft.testbackend.entity.Result;
import com.wiredcraft.testbackend.entity.User;
import com.wiredcraft.testbackend.entity.param.PageParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Override
    public Result<User> getUserById(Long id) {
        Optional<User> optional = userRepository.findById(id);
        if (optional.isPresent()) {
            return Result.success(optional.get());
        }
        return Result.success();
    }

    @Override
    public Result<PageResult<User>> getUserList(User user, PageParam pageParam) {
        Example<User> example = Example.of(user);
        Pageable pageable = PageRequest.of(pageParam.getPage(), pageParam.getPageSize());
        Page<User> page = userRepository.findAll(example, pageable);
        return Result.success(new PageResult<>(page));
    }

    @Override
    public Result<User> createUser(User user) {
        LOGGER.info("createUser, param={}", JSON.toJSONString(user));
        user.setCreatedAt(new Date());
        user.setUpdatedAt(user.getCreatedAt());
        User res = userRepository.save(user);
        return Result.success(res);
    }

    @Override
    public Result<User> updateUser(Long id, User user) {
        LOGGER.info("updateUser, id={}, user={}", id, JSON.toJSONString(user));
        if (user == null) {
            return Result.error("User cannot be null!");
        }
        Optional<User> optional = userRepository.findById(id);
        if (!optional.isPresent()) {
            return Result.error("User does not exist, update failed!");
        }
        user.setId(id);
        user.setCreatedAt(optional.get().getCreatedAt());
        user.setUpdatedAt(new Date());
        User res = userRepository.save(user);
        return Result.success(res);
    }

    @Override
    public Result<Boolean> deleteUserById(Long id) {
        LOGGER.info("deleteUserById, id={}", id);
        Optional<User> optional = userRepository.findById(id);
        if (!optional.isPresent()) {
            LOGGER.warn("deleteUserById failed, user does not exist, id={}", id);
            return Result.error("User does not exist, deletion failed!");
        }
        userRepository.deleteById(id);
        return Result.success(Boolean.TRUE);
    }

}
