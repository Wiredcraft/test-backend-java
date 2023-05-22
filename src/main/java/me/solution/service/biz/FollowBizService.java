package me.solution.service.biz;

import me.solution.model.domain.Follower;
import me.solution.model.domain.Following;
import me.solution.model.domain.User;
import me.solution.service.FollowerService;
import me.solution.service.FollowingService;
import me.solution.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

/**
 * follow biz service
 * the amount following/follower relation may be very large
 * considering this, the relations maybe sharding
 * 1. two tables
 * 2. using distributed transaction (like eventual consistency)
 *
 * @author davincix
 * @since 2023/5/22 05:15
 */
@Service
public class FollowBizService {
    @Autowired
    private UserService userService;
    @Autowired
    private FollowingService followingService;
    @Autowired
    private FollowerService followerService;

    @Transactional(rollbackFor = Throwable.class)
    public void addFollowing(Long myId, Long followingId) {
        User following = userService.getUserById(followingId);
        if (following == null) {
            return;
        }

        Following followingSaver = Following.builder()
                .followerId(myId)
                .followingId(followingId)
                .build();
        Follower followerSaver = Follower.builder()
                .followerId(myId)
                .followingId(followingId)
                .build();

        followingService.addFollowing(followingSaver);
        followerService.addFollower(followerSaver);
    }

    @Transactional(rollbackFor = Throwable.class)
    public void unFollowing(Long myId, Long followingId) {
        User following = userService.getUserById(followingId);
        if (following == null) {
            return;
        }

        Following followingDeleter = Following.builder()
                .followerId(myId)
                .followingId(followingId)
                .build();
        Follower followerDeleter = Follower.builder()
                .followerId(myId)
                .followingId(followingId)
                .build();

        followingService.delFollowing(followingDeleter);
        followerService.delFollower(followerDeleter);
    }

    public List<User> listNearbyFriends(String name) {
        // TODO: 2023/5/22 redis geo-hash
        return Collections.emptyList();
    }
}
