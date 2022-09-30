package com.coffee.user.service;

import com.coffee.user.request.UserCreateParam;
import com.coffee.user.response.info.UserInfo;
import com.coffee.user.request.UserUpdateParam;
import com.coffee.user.response.info.NearbyFriendsInfo;

/**
 * BizUser服务接口
 *
 */
public interface UserService {
    /**
     * 创建用户
     */
    UserInfo create(UserCreateParam createParam);

    /**
     * 根据用户ID，执行用户注销,做逻辑删除
     */
    Boolean delete(String userId);

    UserInfo findOne(String id);

    UserInfo findByUserName(String userName);

    Boolean update(UserUpdateParam updateParam);

}
