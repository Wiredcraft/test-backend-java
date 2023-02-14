package com.w.t.module.controller;

import com.w.t.module.entity.User;
import com.w.t.module.service.impl.FollowService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.w.t.common.core.response.R;
import com.w.t.common.util.storage.LocalStorageUtil;

import java.util.List;

/**
 * @author zhangxiang
 * @date 2023/02/09
 */
@Api(value = "关注管理")
@RestController
@RequestMapping("/follow")
public class FollowController {

    @Autowired
    private FollowService followService;

    @ApiOperation("subscribe a user")
    @PostMapping("/subscribe")
    public R subscribe(@RequestParam Long followerId) {
        Long followingId = LocalStorageUtil.getCurrentUserId();
        followService.subscribe(followerId, followingId);
        return R.success();
    }

    @ApiOperation("unsubscribe a user")
    @PostMapping("/unsubscribe")
    public R unsubscribe(@RequestParam Long followerId) {
        Long followingId = LocalStorageUtil.getCurrentUserId();
        followService.unsubscribe(followerId, followingId);
        return R.success();
    }

    @ApiOperation("query nearby users")
    @PostMapping("/nearby")
    public R<List<User>> nearby(@RequestParam String userName) {
        List<User> userList=followService.nearbyUsers(userName);
        return R.success(userList);
    }

}
