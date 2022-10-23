package com.craig.user.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.craig.user.entity.UserFollower;
import com.craig.user.entity.dto.SimpleFollowerDto;
import com.craig.user.entity.dto.SimpleFollowingDto;

@Mapper
public interface UserFollowerMapper extends BaseMapper<UserFollower> {

    @Select({
            "SELECT uf.follower_id AS followerId, u.name AS followerName ",
            "FROM t_user_follower uf",
            "LEFT JOIN t_user u ON u.id = uf.follower_id AND u.deleted = 0",
            "WHERE uf.deleted = 0 AND uf.user_id = #{userId}" })
    List<SimpleFollowerDto> getFollowers(Long userId);
    
    @Select({
        "SELECT uf.user_Id AS followingId, u.name AS followingName ",
            "FROM t_user_follower uf",
            "LEFT JOIN t_user u ON u.id = uf.user_id AND u.deleted = 0",
            "WHERE uf.deleted = 0 AND uf.follower_Id = #{userId}" 
    })
    List<SimpleFollowingDto> getFollowings(Long userId);
}
