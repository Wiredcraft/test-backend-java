package com.craig.user.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.craig.user.entity.UserFollowing;
import com.craig.user.entity.dto.SimpleUserFollowerDto;

@Mapper
public interface UserFollowingMapper extends BaseMapper<UserFollowing> {

    @Select({
            "SELECT uf.follower_id AS followerId, u.name AS followerName ",
            "FROM t_user_following uf",
            "LEFT JOIN t_user u ON u.id = uf.follower_id AND u.deleted = 0",
            "WHERE uf.deleted = 0 AND uf.user_id = #{userId}" })
    List<SimpleUserFollowerDto> getFollowers(Long userId);
}
