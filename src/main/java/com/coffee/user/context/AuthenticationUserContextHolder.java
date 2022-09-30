package com.coffee.user.context;

import com.coffee.user.response.info.UserInfo;

/**
 * user容器
 */

public class AuthenticationUserContextHolder {

    private static final ThreadLocal<UserInfo> contextHolder = new ThreadLocal<>();

    public static void setUserInfo(UserInfo userInfo) {
        contextHolder.set(userInfo);
    }
    public static void clearContext() {
        contextHolder.remove();
    }
    /**
     * 获取当前上下文认证用户信息
     *
     * @return 认证用户信息
     */
    public static UserInfo getUserInfo() {
        return contextHolder.get();
    }

    /**
     * 获取当前登录用户的用户ID
     *
     * @return  当前登录用户的用户ID
     */
    public static String getCurrentUserId() {
        UserInfo userInfo = getUserInfo();
        if (userInfo == null) {
            return null;
        }
        return userInfo.getId();
    }


}
