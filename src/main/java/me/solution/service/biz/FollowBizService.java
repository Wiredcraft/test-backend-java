package me.solution.service.biz;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import lombok.val;
import me.solution.common.converter.UserConverter;
import me.solution.common.utils.BizChecker;
import me.solution.model.domain.Follower;
import me.solution.model.domain.Following;
import me.solution.model.domain.User;
import me.solution.model.reqresp.FollowResp;
import me.solution.service.FollowerService;
import me.solution.service.FollowingService;
import me.solution.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

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
    @Autowired
    private UserConverter userConverter;

    /**
     * default following/follower page size
     */
    private static final int DEFAULT_FOLLOW_LIST_CNT = 100;

    @Transactional(rollbackFor = Throwable.class)
    public void addFollowing(Long myId, Long followingId) {
        val following = userService.getUserById(followingId, false);
        BizChecker.checkUserExist(following);

        if (Objects.equals(myId, followingId)) {
            return;
        }

        Following followingSaver = Following.builder().followerId(myId).followingId(followingId).build();
        Follower followerSaver = Follower.builder().followerId(myId).followingId(followingId).build();

        followingService.addFollowing(followingSaver);
        followerService.addFollower(followerSaver);
    }

    @Transactional(rollbackFor = Throwable.class)
    public void unFollowing(Long myId, Long followingId) {
        User following = userService.getUserById(followingId, false);
        BizChecker.checkUserExist(following);

        if (Objects.equals(myId, followingId)) {
            return;
        }

        Following followingDeleter = Following.builder().followerId(myId).followingId(followingId).build();
        Follower followerDeleter = Follower.builder().followerId(myId).followingId(followingId).build();

        followingService.delFollowing(followingDeleter);
        followerService.delFollower(followerDeleter);
    }

    public List<FollowResp> listFollowings(Long myUserId) {
        // my followings
        val followings = followingService.listFollowings(myUserId, DEFAULT_FOLLOW_LIST_CNT);
        if (CollectionUtils.isEmpty(followings)) {
            return Collections.emptyList();
        }
        Set<Long> followingIds = followings.stream().map(Following::getFollowingId).collect(Collectors.toSet());
        // my following user and id-user map
        List<User> users = userService.listByIds(followingIds, false);
        ImmutableMap<Long, User> idUserMap = Maps.uniqueIndex(users, User::getId);


        // my followers
        List<Follower> followers = followerService.listFollowers(myUserId, followingIds);
        Set<Long> followerIds = followers.stream().map(Follower::getFollowerId).collect(Collectors.toSet());

        return followings.stream()
                .map(x -> {
                    // get user in map
                    User user = idUserMap.getOrDefault(x.getFollowingId(), null);
                    if (user == null) {
                        return null;
                    }
                    FollowResp resp = userConverter.model2FollowResp(user);
                    // following and follower
                    resp.setBeingFriend(followerIds.contains(x.getFollowingId()));

                    return resp;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}
