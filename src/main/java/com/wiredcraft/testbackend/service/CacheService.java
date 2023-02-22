package com.wiredcraft.testbackend.service;

import com.wiredcraft.testbackend.entity.PageResult;
import com.wiredcraft.testbackend.entity.User;
import com.wiredcraft.testbackend.entity.param.PageParam;

public interface CacheService {

    User getUserByIdFromCache(Long userId);

    void addUserToCache(User user);

    User getUserByNameFromCache(String userName);

    void addUserNameToCache(User user);

    void removeUserNameCache(String userName);

    void removeUserCache(Long userId);

    void addFanToCache(Long userId, Long... fanUserId);

    void addFollowToCache(Long userId, Long... followUserId);

    void removeFanFromCache(Long userId, Long fanUserId);

    void removeFollowFromCache(Long userId, Long followUserId);

    PageResult<Long> getFanUserIdFromCache(Long userId, PageParam pageParam);

    PageResult<Long> getFollowUserIdFromCache(Long userId, PageParam pageParam);
}
