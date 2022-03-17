package com.zhangyongxin.demo.mapper;

import com.zhangyongxin.demo.model.security.UserPermission;
import tk.mybatis.mapper.common.Mapper;

import java.util.Set;

/**
 * @Auther zhangyongxin
 * @date 2022/3/17 下午8:16
 */
public interface UserPermissionMapper extends Mapper<UserPermission> {

    Set<UserPermission> findUserPermissions(String username);

}
