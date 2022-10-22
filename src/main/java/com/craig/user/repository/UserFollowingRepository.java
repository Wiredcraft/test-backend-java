package com.craig.user.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.craig.user.entity.UserFollowing;
import com.craig.user.entity.dto.SimpleUserFollowerDto;
import com.craig.user.mapper.UserFollowingMapper;

@Repository
public class UserFollowingRepository extends ServiceImpl<UserFollowingMapper, UserFollowing> {
    /**
     * get the followers that followered the user whoes id is the userId
     * @param userId the user who get 
     * @return
     */
    public List<SimpleUserFollowerDto> getFollowers(Long userId) {
        return this.baseMapper.getFollowers(userId);
    }
}
