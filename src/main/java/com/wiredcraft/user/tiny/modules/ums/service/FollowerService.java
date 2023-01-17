package com.wiredcraft.user.tiny.modules.ums.service;

import com.wiredcraft.user.tiny.modules.ums.model.Follower;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author
 * @since 2023-01-14
 */
public interface FollowerService extends IService<Follower> {


    boolean follow(String followName, String currentUser);

    List<String> getUserFollow(String currentUser);
}
