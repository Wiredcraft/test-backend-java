package com.coffee.user.request;

import lombok.Data;

@Data
public class FriendCreateParam {
    /**
     * 用户id
     */
    private String userId;

    /**
     * 用户朋友的userId
     */
    private String friendId;

}
