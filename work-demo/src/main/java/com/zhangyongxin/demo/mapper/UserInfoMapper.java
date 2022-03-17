package com.zhangyongxin.demo.mapper;

import com.zhangyongxin.demo.model.UserInfo;
import tk.mybatis.mapper.common.Mapper;

/**
 * 用户信息管理dao
 * @author zhangyongxin
 * @date 2022-03-17 20:07:50
 */
public interface UserInfoMapper extends Mapper<UserInfo> {

    UserInfo findByUsername(String username);

}