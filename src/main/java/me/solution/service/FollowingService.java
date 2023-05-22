package me.solution.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import me.solution.mapper.FollowingMapper;
import me.solution.model.domain.Following;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * following service
 *
 * @author davincix
 * @since 2023/5/20 19:28
 */
@Service
public class FollowingService {
    @Autowired
    private FollowingMapper followingMapper;

    public void addFollowing(Following saver) {
        Optional.ofNullable(saver)
                .ifPresent(followingMapper::insert);
    }

    public void delFollowing(Following followingDeleter) {
        Long followerId = followingDeleter.getFollowerId();
        Long followingId = followingDeleter.getFollowingId();
        if (followerId == null || followingId == null) {
            return;
        }

        LambdaQueryWrapper<Following> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Following::getFollowerId, followerId);
        wrapper.eq(Following::getFollowingId, followingId);
        followingMapper.delete(wrapper);
    }
}
