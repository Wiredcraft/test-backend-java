package com.wiredcraft.test.user.model.req;

import lombok.Data;

/**
 * 关注和取关
 */
@Data
public class FriendsReq {

    /**
     * 被关注的User ID
     */
    private int followedUserId;

    /**
     * 关注的User ID
     */
    private int followerId;

}
