package com.coffee.user.service;

import com.coffee.user.request.FriendCreateParam;
import com.coffee.user.response.info.FriendInfo;

import java.util.List;


public interface FriendService {
    boolean create(FriendCreateParam createParam);

    boolean delete(String id);

    List<FriendInfo> findByUserId(String userId);
}
