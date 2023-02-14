package com.w.t.module.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.w.t.module.entity.Follow;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface FollowMapper extends BaseMapper<Follow> {

    @Select("select following_id from follow_user where follower_id=${followerId}")
    List<Long> selectFollowersByFollowingId(Long followerId);

}
