package com.craig.user.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.craig.user.entity.UserCoordinate;
import com.craig.user.entity.dto.NearbyUserDto;

@Mapper
public interface UserCoordinateMapper extends BaseMapper<UserCoordinate> {

    @Select({
            "SELECT u.id AS user_id, u.name AS user_name, coord_rank.distance FROM t_user u RIGHT JOIN (",
            "SELECT c1.`user_id`, ST_LENGTH(LineString(c1.last_coord, c2.last_coord)) AS distance",
            "FROM t_user_coordinate c1 ",
            "LEFT JOIN (select user_id, last_coord from t_user_coordinate c where c.user_id = #{userId})",
            "AS c2 ON 1 =1",
            "WHERE c1.user_id <> #{userId}",
            "ORDER BY distance ASC ",
            "LIMIT #{top}",
            ") AS coord_rank ON coord_rank.user_id = u.id;",
    })
    List<NearbyUserDto> getNearbyUsers(@Param("userId") Long userId, @Param("top") Integer topN);
}
