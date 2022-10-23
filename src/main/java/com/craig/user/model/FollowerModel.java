package com.craig.user.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.AccessMode;
import lombok.Data;

@Data
public class FollowerModel {
    private Long id;

    private Long userId;

    private Long followerId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(accessMode = AccessMode.READ_ONLY)
    private Date createdAt;
}
