package com.coffee.user.dao.mapper;

import com.coffee.user.domain.po.UserPO;
import org.apache.ibatis.annotations.*;
import org.mapstruct.Mapper;

/**
 * User的MyBatis数据访问mapper
 */
@Mapper
public interface UserMapper {

    @Insert(" INSERT INTO user (name, dob, password, address, description)" +
            " VALUES (#{po.name}, #{po.dob}, #{po.password}, #{po.address}, #{po.description})")
    Integer create(@Param("po") UserPO bizUserPO);

    @Select(" SELECT id, name, dob, password, address, description, created_at createdAt, updated_at updatedAt " +
            " FROM user WHERE id=#{userId} AND is_deleted = 0")
    UserPO findOne(@Param("userId") String userId);

    @Update("UPDATE user set is_deleted = 1 WHERE id=#{userId}")
    Integer delete(@Param("userId") String userId);

    @Update("<script>" +
            "UPDATE user " +
            "<set>" +
            "<if test='po.address != null'>address = #{po.address}, </if>" +
            "<if test='po.dob != null'>dob = #{po.dob}, </if>" +
            "<if test='po.address != null'>address = #{po.address}, </if>" +
            "<if test='po.description != null'>description = #{po.description}, </if>" +
            "</set> " +
            "WHERE id=#{po.id}" +
            "</script>")
    Integer update(@Param("po") UserPO userPO);

    @Select(" SELECT id, name, dob, password, address, description, created_at createdAt, " +
            " updated_at updatedAt, is_deleted isDeleted " +
            " FROM user WHERE name=#{userName} ")
    UserPO findByUserName(@Param("userName") String userName);

}
