package com.wiredcraft.test.user.controller;

import com.wiredcraft.test.user.model.vo.UserVO;
import com.wiredcraft.test.user.service.FriendsService;
import com.wiredcraft.test.user.tool.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Friends API
 */
@RestController
@RequestMapping("/api/friends")
public class FriendsController {

    @Autowired
    private FriendsService friendsService;

    /**
     * 关注
     *
     * @param userId         当前用户
     * @param followedUserId 被关注人
     * @return
     */
    @PostMapping("/follow")
    public JsonResult<Void> follow(@RequestParam int userId, @RequestParam int followedUserId) {
        friendsService.follow(followedUserId, userId);
        return JsonResult.success();
    }

    /**
     * 取消关注
     *
     * @param userId        当前用户
     * @param followUsereId 被关注的人
     * @return
     */
    @PostMapping("/follow/cancel")
    public JsonResult<Void> cancelFollow(@RequestParam int userId, @RequestParam int followUsereId) {
        friendsService.cannelFollow(followUsereId, userId);
        return JsonResult.success();
    }

    /**
     * 关注我的人
     *
     * @param userId
     * @return
     */
    @GetMapping("/fans")
    public JsonResult<List<UserVO>> fans(@RequestParam int userId) {
        List<UserVO> fans = friendsService.fans(userId);
        return JsonResult.success(fans);
    }

    /**
     * 我关注的人
     *
     * @param userId
     * @return
     */
    @GetMapping("/followers")
    public JsonResult<List<UserVO>> followers(@RequestParam int userId) {
        List<UserVO> followers = friendsService.fans(userId);
        return JsonResult.success(followers);
    }

    /**
     * 关注我的数量
     *
     * @param userId
     * @return
     */
    @GetMapping("/fans/count")
    public JsonResult<Integer> countFans(@RequestParam int userId) {
        return JsonResult.success();
    }

    /**
     * 我关注的数量
     *
     * @param userId
     * @return
     */
    @GetMapping("/followers/count")
    public JsonResult<Integer> countFollowers(@RequestParam int userId) {
        return JsonResult.success();
    }

    /**
     * 获取附近的朋友
     *
     * @param name 用户名
     * @return
     */
    @GetMapping("/nearby")
    public JsonResult<List<UserVO>> nearbyFriends(@RequestParam String name) {

        // 1 根据name查询坐标，查用户表

        // 2 查询我的朋友的id，查关系表

        // 3 半径内符合条件的朋友，查用户表，条件是坐标范围和朋友ID集合

        return JsonResult.success();
    }

}
