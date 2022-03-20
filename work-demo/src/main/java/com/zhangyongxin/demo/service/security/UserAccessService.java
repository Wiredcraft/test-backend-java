package com.zhangyongxin.demo.service.security;

import com.google.common.collect.Sets;
import com.zhangyongxin.demo.mapper.UserInfoMapper;
import com.zhangyongxin.demo.mapper.UserPermissionMapper;
import com.zhangyongxin.demo.model.user.UserInfo;
import com.zhangyongxin.demo.model.security.User;
import com.zhangyongxin.demo.model.security.UserAuthority;
import com.zhangyongxin.demo.model.security.UserPermission;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Set;

/**
 * 用户，用户权限获得服务
 *
 * @Auther zhangyongxin
 * @date 2022/3/20 下午3:32
 */
@Component
@Slf4j
public class UserAccessService implements UserDetailsService {

    @Resource
    private UserInfoMapper userInfoMapper;

    @Resource
    private UserPermissionMapper permissionMapper;

    @Override
    @Cacheable(value="userDetails",key="#name")
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        UserInfo userInfo = userInfoMapper.findByUsername(name);
        User user;
        if (userInfo == null) {
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", name));
        }
        user = new User(userInfo.getUsername(), userInfo.getPassword());
        Set<UserPermission> permissionSet = permissionMapper.findUserPermissions(name);
        Set<UserAuthority> userAuthorities = Sets.newHashSetWithExpectedSize(permissionSet.size());
        if (!CollectionUtils.isEmpty(permissionSet)) {
            for (UserPermission permission : permissionSet) {
                UserAuthority userAuthority = new UserAuthority(permission.getPermission(), permission.getName());
                userAuthorities.add(userAuthority);
            }
            user.setAuthorities(userAuthorities);
        }
        log.info("user found with username {}.",name);
        return user;
    }
}
