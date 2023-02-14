package com.w.t.module.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.w.t.module.service.IFollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.w.t.module.entity.Follow;
import com.w.t.module.entity.User;
import com.w.t.module.mapper.FollowMapper;
import com.w.t.module.mapper.UserMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class FollowService implements IFollowService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    FollowMapper followMapper;

    @Override
    public void subscribe(Long followerId, Long followingId) {
        LambdaQueryWrapper<Follow> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Follow::getFollowerId, followerId);
        wrapper.eq(Follow::getFollowingId, followingId);
        int followCount = followMapper.selectCount(wrapper);
        if (followCount > 0) {
            throw new RuntimeException("the user has already followed.");
        }
        Follow follow = new Follow();
        follow.setFollowerId(followerId);
        follow.setFollowingId(followingId);
        followMapper.insert(follow);
    }

    @Override
    public void unsubscribe(Long followerId, Long followingId) {
        LambdaQueryWrapper<Follow> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Follow::getFollowerId, followerId);
        wrapper.eq(Follow::getFollowingId, followingId);
        Follow follow = followMapper.selectOne(wrapper);
        if (Objects.isNull(follow)) {
            throw new RuntimeException("the following does not followed.");
        }
        followMapper.deleteById(follow.getId());
    }

    @Override
    public List<User> nearbyUsers(String username) {
        //query the user's longitude or latitude  by name
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getName, username);
        User user = userMapper.selectOne(wrapper);
        if (Objects.isNull(user)) {
            throw new RuntimeException("the user can't be find.");
        }

        double latitude = user.getLatitude();
        double longitude = user.getLongitude();

        //query nearby user list
        double dlng = 2 * Math.asin(Math.sin(1 / (2 * 6371)) / Math.cos(latitude * Math.PI / 180));
        dlng = dlng * 180 / Math.PI;
        double dlat = 1 / 6371;
        dlat = dlat * 180 / Math.PI;
        double minlat = latitude - dlat;
        double maxlat = latitude + dlat;
        double minlng = longitude - dlng;
        double maxlng = longitude + dlng;
        List<User> nearbyUsers = userMapper.select(minlng, maxlng, minlat, maxlat);

        //query  nearby following list
        List<Long> follows = followMapper.selectFollowersByFollowingId(user.getId());
        List<User> result = new ArrayList<>();
        for (User u : nearbyUsers) {
            for (Long followingId : follows) {
                if (followingId.equals(u.getId())) {
                    result.add(u);
                }
            }
        }
        return result;
    }

}
