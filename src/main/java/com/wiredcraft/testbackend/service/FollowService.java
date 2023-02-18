package com.wiredcraft.testbackend.service;

import com.wiredcraft.testbackend.entity.PageResult;
import com.wiredcraft.testbackend.entity.Result;
import com.wiredcraft.testbackend.entity.User;
import com.wiredcraft.testbackend.entity.param.PageParam;

public interface FollowService {

    /**
     * query fans list by userId
     */
    Result<PageResult<User>> getFanById(Long userId, PageParam pageParam);

    /**
     * query following list by userId
     */
    Result<PageResult<User>> getFollowById(Long userId, PageParam pageParam);

    /**
     * follow a userId
     */
    Result<Boolean> follow(Long originalUserId, Long targetUserId);

    /**
     * unfollow a userId
     */
    Result<Boolean> unfollow(Long originalUserId, Long targetUserId);

}
