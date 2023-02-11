package com.wiredcraft.test.user.service.impl;

import com.wiredcraft.test.user.model.vo.UserVO;
import com.wiredcraft.test.user.service.FriendsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FriendsServiceImpl implements FriendsService {

    @Override
    public boolean follow(int followedUserId, int followerId) {
        //TODO: 在关联表中添加一条关联记录
        return true;
    }

    @Override
    public boolean cannelFollow(int followedUserId, int followerId) {
        // TODO: 在关联表中删除一条关联记录
        return true;
    }

    @Override
    public List<UserVO> fans(int userId) {
        // TODO:
        //  1 分页查关联表ID
        //  2 去用户表查询用户信息
        return null;
    }

    @Override
    public List<UserVO> followers(int userId) {
        // TODO:
        //  1 分页查关联表ID
        //  2 去用户表查询用户信息
        return null;
    }

}
