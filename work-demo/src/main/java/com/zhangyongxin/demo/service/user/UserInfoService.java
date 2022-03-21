package com.zhangyongxin.demo.service.user;

import com.zhangyongxin.demo.model.user.UserInfo;

/**
 * Created by dinggh on 18/6/30.
 */
public interface UserInfoService {

    int addOne(UserInfo userInfo);

    boolean updateOne(UserInfo userInfo);

    UserInfo findByName(String username);

    boolean deleteOne(int id);

}
