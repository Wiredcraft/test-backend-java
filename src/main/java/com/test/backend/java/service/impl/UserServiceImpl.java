package com.test.backend.java.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.test.backend.java.entity.Friend;
import com.test.backend.java.entity.User;
import com.test.backend.java.mapper.UserMapper;
import com.test.backend.java.service.FriendService;
import com.test.backend.java.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Rock Jiang
 * @since 2022-04-16
 */
@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private FriendService friendService;

    @Override
    public List<User> getUserFriends(String userId) {
        LambdaQueryWrapper<Friend> friendWrapper = new LambdaQueryWrapper<>();
        friendWrapper.eq(Friend::getUserId, userId);
        List<Friend> list = friendService.list(friendWrapper);

        List<String> friendIdList = list.stream().map(Friend::getFriendId).collect(Collectors.toList());

        LambdaQueryWrapper<User> userWrapper = new LambdaQueryWrapper<>();
        userWrapper.in(User::getUserId, friendIdList);
        List<User> userList = list(userWrapper);

        log.info("user id: {} friends: {}", userId, JSONObject.toJSONString(userList));

        return userList;
    }
}
