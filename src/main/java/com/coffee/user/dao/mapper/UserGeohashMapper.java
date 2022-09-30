package com.coffee.user.dao.mapper;

import com.coffee.user.domain.po.UserGeohashPO;
import com.coffee.user.domain.po.UserPO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserGeohashMapper {
    @Insert(" INSERT INTO user_geohash (user_id, longitude, latitude, geo_code )" +
            " VALUES (#{po.userId}, #{po.longitude}, #{po.latitude}, #{po.geoCode} )")
    Integer create(@Param("po") UserGeohashPO userGeohashPO);

    @Update("UPDATE user set is_deleted = 1 WHERE id=#{id}")
    Integer delete(@Param("id") String id);

    @Update("<script>" +
            "UPDATE user_geohash " +
            "<set>" +
            "<if test='po.longitude != null'>longitude = #{po.longitude}, </if>" +
            "<if test='po.latitude != null'>latitude = #{po.latitude}, </if>" +
            "<if test='po.geoCode != null'>geo_code = #{po.geoCode}, </if>" +
            "</set> " +
            "WHERE id=#{po.id}" +
            "</script>")
    Integer update(@Param("po") UserGeohashPO userGeohashPO);

    @Select(" SELECT id, user_id userId, longitude, latitude, geo_code geoCode " +
            " FROM user_geohash WHERE user_id = #{userId} AND is_deleted = 0")
    UserGeohashPO findByUserId(@Param("userId") String userId);

    @Select("<script>" +
            " SELECT user_id userId, longitude, latitude, geo_code geoCode " +
            " FROM user_geohash " +
            " WHERE is_deleted = 0 " +
            " AND geo_code like '${geoCode}%' " +
            "</script>")
    List<UserGeohashPO> findByGeoCode(@Param("geoCode") String geoCode);
}
