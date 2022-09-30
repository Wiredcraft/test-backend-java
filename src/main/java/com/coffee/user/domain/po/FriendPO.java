package com.coffee.user.domain.po;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FriendPO extends BasePO{
    /**
     * 用户id
     */
    private String userId;
    /**
     * 用户名字
     */
    private String userName;
    /**
     * 用户朋友的userId
     */
    private String friendId;
    /**
     * 用户朋友的名字
     */
    private String friendName;
}
