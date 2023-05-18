package com.jiang.test.backend.service;

import com.jiang.test.backend.entity.User;

import java.util.List;

public interface FriendService {

    List<User> getFollowers(int userId);

    void addFollower(int userId, int followerId);

    void removeFollower(int userId, int followerId);

    List<User> getFriends(int userId);

    List<User> getCommonFriends(int userId1, int userId2);

    List<User> getNearbyFriends(int userId, double distance);

}
