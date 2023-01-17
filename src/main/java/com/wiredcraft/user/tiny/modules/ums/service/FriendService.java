package com.wiredcraft.user.tiny.modules.ums.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wiredcraft.user.tiny.modules.ums.model.Friend;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author YUAO
 * @since 2023-01-14
 */
public interface FriendService extends IService<Friend> {

    Page<Friend> list(String currentUSer, Integer pageSize, Integer pageNum);

    List<String> nearByFriends(String currentUSer, Double latitude, Double longitude);
}
