package com.craig.user.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.craig.user.entity.User;
import com.craig.user.entity.UserFollower;
import com.craig.user.entity.dto.SimpleFollowerDto;
import com.craig.user.entity.dto.SimpleFollowingDto;
import com.craig.user.model.FollowerModel;
import com.craig.user.model.SimpleUserModel;
import com.craig.user.repository.UserFollowerRepository;
import com.craig.user.repository.UserRepository;
import com.craig.user.service.FollowerService;

@Service
public class FollowerServiceImpl implements FollowerService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserFollowerRepository userFollowerRepository;

    @Override
    public List<SimpleUserModel> getFollowings(Long userId) {
        List<SimpleFollowingDto> followingDto = userFollowerRepository.getFollowings(userId);
        if (CollectionUtils.isEmpty(followingDto)) {
            return null;
        }
        List<SimpleUserModel> followingModels = followingDto.stream().map(c -> {
            SimpleUserModel followerModel = new SimpleUserModel();
            followerModel.setUserId(c.getFollowingId());
            followerModel.setUserName(c.getFollowingName());
            return followerModel;
        }).collect(Collectors.toList());
        return followingModels;
    }

    @Override
    public List<SimpleUserModel> getFollowers(Long userId) {
        List<SimpleFollowerDto> followerDto = userFollowerRepository.getFollowers(userId);
        if (CollectionUtils.isEmpty(followerDto)) {
            return null;
        }
        List<SimpleUserModel> followerModels = followerDto.stream().map(c -> {
            SimpleUserModel followerModel = new SimpleUserModel();
            followerModel.setUserId(c.getFollowerId());
            followerModel.setUserName(c.getFollowerName());
            return followerModel;
        }).collect(Collectors.toList());
        return followerModels;
    }


    @Override
    public FollowerModel addFollowers(Long userId, Long followerId) {
        User user = userRepository.getUser(userId);
        if (user == null) {
            return null;
        }

        UserFollower existRecord = userFollowerRepository.getRecord(userId, followerId);
        if (existRecord != null) {
            // deal with existRecord, for idempotent
            FollowerModel model = new FollowerModel();
            BeanUtils.copyProperties(existRecord, model);
            return model;
        }

        UserFollower userFollower = userFollowerRepository.addUserFollower(userId, followerId);

        FollowerModel model = new FollowerModel();
        BeanUtils.copyProperties(userFollower, model);
        return model;
    }

    @Override
    public Boolean deleteFollower(Long userId, Long follwerId) {
        UserFollower record = userFollowerRepository.getRecord(userId, follwerId);
        if (record == null) {
            return false;
        }
        userFollowerRepository.delete(record);
        return true;
    }

}
