package com.wiredcraft.user.tiny.modules.ums.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;

@Data
@EqualsAndHashCode(callSuper = false)
public class FollowParam {

    @NotEmpty
    @ApiModelProperty(value = "follower Name",required = true)
    private String followerName;
}
