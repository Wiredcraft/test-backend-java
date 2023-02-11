package com.wiredcraft.test.user.service;

import com.wiredcraft.test.user.model.vo.UserVO;
import org.apache.catalina.User;

import java.util.List;

/**
 * 关注和取关
 */
public interface FriendsService {

    /**
     * 关注
     * @param followedUserId 被关注人
     * @param followerId 当前用户
     * @return
     */
    boolean follow(int followedUserId, int followerId);

    /**
     * 取关
     * @param followedUserId 被关注人
     * @param followerId 当前用户
     * @return
     */
    boolean cannelFollow(int followedUserId, int followerId);

    /**
     * 查询哪些人关注了我
     *
     * @param userId 当前用户ID
     * @return
     */
    List<UserVO> fans(int userId);

    /**
     * 我关注了哪些人
     * @param userId 当前用户
     * @return
     */
    List<UserVO> followers(int userId);

}
