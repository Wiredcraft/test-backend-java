package com.coffee.user.response.info;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("用户朋友")
public class FriendInfo {
//    @ApiModelProperty("用户id")
//    private String userId;
//
//    @ApiModelProperty("用户名字")
//    private String userName;

    @ApiModelProperty("用户朋友的userId")
    private String friendId;

    @ApiModelProperty("用户朋友的名字")
    private String friendName;
}
