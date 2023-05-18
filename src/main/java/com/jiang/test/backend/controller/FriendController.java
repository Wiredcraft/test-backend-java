package com.jiang.test.backend.controller;

import com.jiang.test.backend.annotation.Authorized;
import com.jiang.test.backend.constant.ApiConstants;
import com.jiang.test.backend.entity.User;
import com.jiang.test.backend.service.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiConstants.COMMON_URL)
public class FriendController {

    @Autowired
    FriendService friendService;

    /**
     * 获取用户的所有关注者
     * @param userId
     * @return List<User>
     */
    @GetMapping("/friend/{userId}")
    @Authorized
    public ResponseEntity<List<User>> getFollowers(@PathVariable int userId) {
        List<User> followers = friendService.getFollowers(userId);
        return ResponseEntity.ok(followers);
    }

    /**
     * 添加关注者
     * 请求体中包含要添加的关注者的ID。
     * @param userId
     * @param followerId
     * @return
     */
    @PostMapping("/friend/{userId}/follower/{followerId}")
    @Authorized
    public ResponseEntity<String> addFollower(@PathVariable int userId, @PathVariable int followerId) {
        friendService.addFollower(userId,followerId);
        return ResponseEntity.ok("Follower added successfully.");
    }

    /**
     * 删除关注者
     * @param userId
     * @param followerId
     * @return
     */
    @DeleteMapping("/friend/{userId}/followers/{followerId}")
    @Authorized
    public ResponseEntity<String> removeFollower(@PathVariable int userId, @PathVariable int followerId) {
        friendService.removeFollower(userId,followerId);
        return ResponseEntity.ok("Follower removed successfully.");
    }

    /**
     * 获取用户的所有好友
     * @param userId
     * @return
     */
    @GetMapping("/friend/{userId}/friends")
    @Authorized
    public ResponseEntity<List<User>> getFriends(@PathVariable int userId) {
        List<User> friends = friendService.getFriends(userId);
        return ResponseEntity.ok(friends);
    }

    /**
     * 获取共同的朋友
     * @param userId
     * @param otherUserId
     * @return
     */
    @GetMapping("/friend/{userId}/friends/common")
    @Authorized
    public ResponseEntity<List<User>> getCommonFriends(@PathVariable int userId, @RequestParam int otherUserId) {
        List<User> commonFriends = friendService.getCommonFriends(userId,otherUserId);
        return ResponseEntity.ok(commonFriends);
    }

    /**
     * 获取最近的朋友信息
     * @param userId
     * @param distance
     * @return
     */
    @GetMapping("/friend/{userName}/distance/{disdance}")
    @Authorized
    public ResponseEntity<List<User>> getNearbyFriends(@PathVariable int userId,
                                                       @PathVariable double distance) {
        List<User> nearbyFriends = friendService.getNearbyFriends(userId,distance);
        return ResponseEntity.ok(nearbyFriends);
    }
}
