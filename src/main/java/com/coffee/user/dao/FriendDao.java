package com.coffee.user.dao;

import com.coffee.user.domain.po.FriendPO;

import java.util.List;

public interface FriendDao {

    boolean create(FriendPO user);

    boolean delete(String id);

    FriendPO findById(String id);

    List<FriendPO> findByUserId(String userId);
}
