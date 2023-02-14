package com.w.t.module.service;

import com.w.t.module.entity.User;

import java.util.List;

public interface IFollowService {

    void subscribe(Long followerId,Long followingId);

    void unsubscribe(Long followerId,Long followingId);

    List<User> nearbyUsers(String username);

}
