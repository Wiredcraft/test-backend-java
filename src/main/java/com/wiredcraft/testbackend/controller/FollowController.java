package com.wiredcraft.testbackend.controller;

import com.wiredcraft.testbackend.entity.PageResult;
import com.wiredcraft.testbackend.entity.Result;
import com.wiredcraft.testbackend.entity.User;
import com.wiredcraft.testbackend.entity.param.PageParam;
import com.wiredcraft.testbackend.service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * relationship api
 */
@RestController
public class FollowController {

    @Autowired
    private FollowService followService;

    /**
     * query fans list by userId
     */
    @GetMapping("fan/query/{userId}")
    public Result<PageResult<User>> getFanById(@PathVariable("userId") Long userId, PageParam pageParam) {
        return followService.getFanById(userId, pageParam);
    }

    /**
     * query follower list by userId
     */
    @GetMapping("follow/query/{userId}")
    public Result<PageResult<User>> getFollowById(@PathVariable("userId") Long userId, PageParam pageParam) {
        return followService.getFollowById(userId, pageParam);
    }

    /**
     * follow a userId
     */
    @PostMapping("follow")
    public Result<Boolean> follow(Long originalUserId, Long targetUserId) {
        return followService.follow(originalUserId, targetUserId);
    }

    /**
     * unfollow a userId
     */
    @DeleteMapping("unfollow/{originalUserId}/{targetUserId}")
    public Result<Boolean> unfollow(@PathVariable("originalUserId") Long originalUserId,
                                    @PathVariable("targetUserId") Long targetUserId) {
        return followService.unfollow(originalUserId, targetUserId);
    }

}
