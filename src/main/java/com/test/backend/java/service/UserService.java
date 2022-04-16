package com.test.backend.java.service;

import com.test.backend.java.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Rock Jiang
 * @since 2022-04-16
 */
public interface UserService extends IService<User> {

    List<User> getUserFriends(String userId);

}
