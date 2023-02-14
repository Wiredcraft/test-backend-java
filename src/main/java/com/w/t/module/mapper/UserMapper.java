package com.w.t.module.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import com.w.t.module.entity.User;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    @Select("SELECT * FROM account_user WHERE 1=1 and (longitude BETWEEN ${minlng} AND ${maxlng}) and (latitude BETWEEN ${minlat} AND ${maxlat})")
    List<User> select(Double minlng, Double maxlng, Double minlat, Double maxlat);
}
