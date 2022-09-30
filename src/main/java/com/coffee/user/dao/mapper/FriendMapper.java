package com.coffee.user.dao.mapper;

import com.coffee.user.domain.po.FriendPO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface FriendMapper {
    @Insert(" INSERT INTO friend (user_id, user_name, friend_id, friend_name)" +
            " VALUES (#{po.userId}, #{po.userName}, #{po.friendId}, #{po.friendName})")
    Integer create(@Param("po") FriendPO po);

    @Select(" SELECT id, user_id userId, user_name userName, friend_id friendId, " +
            " friend_name friendName, is_deleted isDeleted, created_at createdAt, " +
            " updated_at updateAt " +
            " FROM friend WHERE user_id=#{userId} and is_deleted = 0 ")
    List<FriendPO> findByUserId(@Param("userId") String userId);

    @Update("UPDATE friend set is_deleted = 1 WHERE id=#{id}")
    Integer delete(@Param("id") String id);

    @Select(" SELECT id, user_id userId, user_name userName, friend_id friendId, " +
            " friend_name friendName, is_deleted isDeleted, created_at createdAt, " +
            " updated_at updatedAt " +
            " FROM friend WHERE id = #{id} ")
    FriendPO findById(@Param("id") String id);
}
