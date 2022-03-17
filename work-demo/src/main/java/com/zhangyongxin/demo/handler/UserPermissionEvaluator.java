package com.zhangyongxin.demo.handler;

import com.zhangyongxin.demo.model.security.UserAuthority;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Set;

/**
 *处理hasPermission expression
 * @Auther zhangyongxin
 * @date 2022/3/20 下午7:44
 */
@Component
public class UserPermissionEvaluator implements PermissionEvaluator {
    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Set<UserAuthority> userAuthoritySet = (Set<UserAuthority>) userDetails.getAuthorities();

        return CollectionUtils.isNotEmpty(userAuthoritySet) && userAuthoritySet.stream()
                .anyMatch(userAuthority -> userAuthority.getAuthority().equals(permission.toString()));
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        return false;
    }
}
