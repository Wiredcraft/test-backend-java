package com.wiredcraft.testbackend.service;

import com.alibaba.fastjson.JSON;
import com.wiredcraft.testbackend.dao.UserRepository;
import com.wiredcraft.testbackend.entity.PageResult;
import com.wiredcraft.testbackend.entity.Result;
import com.wiredcraft.testbackend.entity.ResultsCode;
import com.wiredcraft.testbackend.entity.User;
import com.wiredcraft.testbackend.entity.param.PageParam;
import com.wiredcraft.testbackend.entity.param.UserParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CacheService cacheService;

    @Override
    public Result<User> getUserById(Long id) {
        User user = cacheService.getUserByIdFromCache(id);
        if (user != null) {
            return Result.success(user);
        }
        Optional<User> optional = userRepository.findById(id);
        if (optional.isPresent()) {
            cacheService.addUserToCache(optional.get());
            return Result.success(optional.get());
        }
        return Result.success();
    }

    @Override
    public List<User> getUserByIds(List<Long> ids) {
        return userRepository.findAllById(ids);
    }

    @Override
    public Result<PageResult<User>> getUserList(User user, PageParam pageParam) {
        Example<User> example = Example.of(user);
        Pageable pageable = PageRequest.of(pageParam.getPage(), pageParam.getPageSize());
        Page<User> page = userRepository.findAll(example, pageable);
        return Result.success(new PageResult<>(page));
    }

    @Override
    public User getUserByName(String userName) {
        User user = cacheService.getUserByNameFromCache(userName);
        if (user != null) {
            return user;
        }

        user = new User();
        user.setName(userName);
        Example<User> example = Example.of(user);
        Optional<User> optional = userRepository.findOne(example);
        if (optional.isPresent()) {
            cacheService.addUserNameToCache(optional.get());
            return optional.get();
        }
        return null;
    }

    @Override
    public Result<User> createUser(User user) {
        LOGGER.info("createUser, param={}", JSON.toJSONString(user));
        User dbUser = getUserByName(user.getName());
        if (dbUser != null) {
            return Result.error(ResultsCode.BAD_REQUEST.getCode(), "User name cannot be repeated");
        }

        user.setCreatedAt(new Date());
        user.setUpdatedAt(user.getCreatedAt());
        User res = userRepository.save(user);
        cacheService.addUserToCache(res);
        return Result.success(res);
    }

    @Override
    public Result<User> updateUser(Long id, UserParam userParam) {
        LOGGER.info("updateUser, id={}, user={}", id, JSON.toJSONString(userParam));
        if (userParam == null) {
            return Result.error("userParam cannot be null!");
        }
        Optional<User> optional = userRepository.findById(id);
        if (!optional.isPresent()) {
            return Result.error("User does not exist, update failed!");
        }

        User oldUser = optional.get();
        if (StringUtils.hasLength(userParam.getName())) {
            oldUser.setName(userParam.getName());
        }
        if (StringUtils.hasLength(userParam.getPassword())) {
            oldUser.setPassword(new BCryptPasswordEncoder().encode(userParam.getPassword()));
        }
        if (userParam.getDob() != null) {
            oldUser.setDob(userParam.getDob());
        }
        if (StringUtils.hasLength(userParam.getAddress())) {
            oldUser.setAddress(userParam.getAddress());
        }
        if (StringUtils.hasLength(userParam.getDescription())) {
            oldUser.setDescription(userParam.getDescription());
        }
        oldUser.setUpdatedAt(new Date());
        User res = userRepository.save(oldUser);

        // add user to cache
        cacheService.addUserToCache(res);
        cacheService.addUserNameToCache(res);
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
        cacheService.removeUserCache(id);
        cacheService.removeUserNameCache(optional.get().getName());
        return Result.success(Boolean.TRUE);
    }

}
