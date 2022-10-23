package com.craig.user.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.craig.user.entity.UserFollower;
import com.craig.user.entity.dto.SimpleFollowerDto;
import com.craig.user.entity.dto.SimpleFollowingDto;
import com.craig.user.mapper.UserFollowerMapper;

@Repository
public class UserFollowerRepository extends ServiceImpl<UserFollowerMapper, UserFollower> {
    /**
     * get the followers that followed the user whoes id is the userId(the user's
     * fans)
     * 
     * @param userId the user who get
     * @return
     */
    public List<SimpleFollowerDto> getFollowers(Long userId) {
        return this.baseMapper.getFollowers(userId);
    }

    /**
     * get the user's following list
     * 
     * @param userId the user who get
     * @return
     */
    public List<SimpleFollowingDto> getFollowings(Long userId) {
        return this.baseMapper.getFollowings(userId);
    }

    /**
     * add follower to the user
     * 
     * @param userId
     * @param followerId
     * @return
     */
    public UserFollower addUserFollower(Long userId, Long followerId) {
        UserFollower userFollower = new UserFollower();
        userFollower.setUserId(userId);
        userFollower.setFollowerId(followerId);
        this.save(userFollower);
        return this.getById(userFollower.getId());
    }

    /**
     * get follower record
     * 
     * @param userId
     * @param followerId
     * @return UserFollower
     */
    public UserFollower getRecord(Long userId, Long followerId) {
        Wrapper<UserFollower> queryWrapper = new QueryWrapper<UserFollower>()
                .lambda()
                .eq(UserFollower::getDeleted, false)
                .eq(UserFollower::getUserId, userId)
                .eq(UserFollower::getFollowerId, followerId);
        return this.getOne(queryWrapper);
    }

    /**
     * delete user follower relation
     * @param record
     */
    public void delete(UserFollower record) {
        Wrapper<UserFollower> updateWrapper = new UpdateWrapper<UserFollower>().lambda()
                .eq(UserFollower::getId, record.getId())
                .set(UserFollower::getDeleted, true);
        this.update(null, updateWrapper);
    }
}
