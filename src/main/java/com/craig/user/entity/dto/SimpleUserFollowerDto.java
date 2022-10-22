package com.craig.user.entity.dto;

import lombok.Data;

@Data
public class SimpleUserFollowerDto {
    private Long followerId;

    private String followerName;
}
