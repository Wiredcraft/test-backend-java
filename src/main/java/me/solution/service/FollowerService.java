package me.solution.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import me.solution.mapper.FollowerMapper;
import me.solution.model.domain.Follower;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * follower service
 *
 * @author davincix
 * @since 2023/5/20 19:28
 */
@Service
public class FollowerService {
    @Autowired
    private FollowerMapper followerMapper;

    public void addFollower(Follower saver) {
        Optional.ofNullable(saver)
                .ifPresent(followerMapper::insert);
    }

    public void delFollower(Follower followerDeleter) {
        Long followingId = followerDeleter.getFollowingId();
        Long followerId = followerDeleter.getFollowerId();
        if (followerId == null || followingId == null) {
            return;
        }

        LambdaQueryWrapper<Follower> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Follower::getFollowingId, followingId);
        wrapper.eq(Follower::getFollowerId, followerId);
        followerMapper.delete(wrapper);
    }
}
