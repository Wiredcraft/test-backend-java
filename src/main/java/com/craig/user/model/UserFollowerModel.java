package com.craig.user.model;

import lombok.Data;

@Data
public class UserFollowerModel {
    private Long followerId;

    private String followerName;
}