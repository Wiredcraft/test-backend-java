package com.craig.user.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.craig.user.entity.User;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    
}
