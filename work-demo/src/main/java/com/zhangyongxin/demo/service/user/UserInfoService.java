package com.zhangyongxin.demo.service.user;

import com.zhangyongxin.demo.model.UserInfo;

/**
 * Created by dinggh on 18/6/30.
 */
public interface UserInfoService {

    int addOne(UserInfo userInfo);

    void updateOne(UserInfo userInfo);

    UserInfo findByName(String username);

}
