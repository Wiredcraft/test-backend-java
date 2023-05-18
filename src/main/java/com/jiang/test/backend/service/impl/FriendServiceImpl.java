package com.jiang.test.backend.service.impl;

import com.jiang.test.backend.entity.Follower;
import com.jiang.test.backend.entity.User;
import com.jiang.test.backend.repository.FollowerRepository;
import com.jiang.test.backend.repository.UserRepository;
import com.jiang.test.backend.service.FriendService;
import com.jiang.test.backend.service.UserService;
import com.jiang.test.backend.utils.DistanceUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FriendServiceImpl implements FriendService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    FollowerRepository followerRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public List<User> getFollowers(int userId) {
        List<Follower> followers = followerRepository.findByUserId(userId);
        // 将 Follower 转换为 User 对象
        List<User> followerUsers = new ArrayList<>();
        for (Follower follower : followers) {
            User followerUser = convertFollowerToUser(follower);
            followerUsers.add(followerUser);
        }
        return followerUsers;
    }

    private User convertFollowerToUser(Follower follower) {
        return userRepository.findById(follower.getFollowerId()).get();
    }

    @Override
    public void addFollower(int userId, int followerId) {
        Optional<Follower> existingFollower = followerRepository.findByUserIdAndFollowerId(userId, followerId);
        if (existingFollower.isPresent()) {
            logger.error("Follower already exists.");
            throw new IllegalArgumentException("Follower already exists.");
        }
        Follower follower = new Follower();
        follower.setUserId(userId);
        follower.setFollowerId(followerId);
        followerRepository.save(follower);
    }

    @Override
    public void removeFollower(int userId, int followerId) {
        Optional<Follower> follower = followerRepository.findByUserIdAndFollowerId(userId, followerId);
        if (follower.isPresent()) {
            followerRepository.delete(follower.get());
        } else {
            logger.error("Follower not found.");
            throw new IllegalArgumentException("Follower not found.");
        }
    }

    @Override
    public List<User> getFriends(int userId) {
        List<Follower> followers = followerRepository.findByUserId(userId);
        List<User> friendsList = new ArrayList<>();
        for (int i = 0; i < followers.size(); i++) {
            List<User> followers1 = getFollowers(followers.get(i).getFollowerId());
            if (CollectionUtils.isEmpty(followers1)){
                friendsList.add(followers1.get(0));
            }
        }
        return friendsList;
    }

    @Override
    public List<User> getCommonFriends(int userId1, int userId2) {

        List<User> commonFriends = new ArrayList<>();

        // 获取用户1的好友列表
        List<User> friends1 = getFriends(userId1);

        // 获取用户2的好友列表
        List<User> friends2 = getFriends(userId2);

        // 遍历用户1的好友列表，判断是否为用户2的好友
        for (User friend1 : friends1) {
            if (friends2.contains(friend1)) {
                commonFriends.add(friend1);
            }
        }

        return commonFriends;
    }

    @Override
    public List<User> getNearbyFriends(int userId, double distance) {

        List<User> nearbyFriendsList = new ArrayList<>();

        User userInfo = userRepository.findById(userId).get();
        Double latitude1 = userInfo.getLatitude();
        Double longitude1 = userInfo.getLongitude();

        //获取所有朋友
        List<User> friendList = getFriends(userId);

        if (CollectionUtils.isEmpty(friendList)) {
            logger.info("No Friend");
            return null;
        }

        //计算小于规定距离的
        for (User user:friendList) {
            double calculateDistance = DistanceUtils.calculateDistance(latitude1, longitude1, user.getLatitude(), user.getLongitude());
            if (calculateDistance<distance){
                nearbyFriendsList.add(user);
            }
        }

        return nearbyFriendsList;
    }

}
