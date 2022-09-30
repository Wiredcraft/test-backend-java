package com.coffee.user.response.info;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@ApiModel("用户附近的朋友")
public class NearbyFriendsInfo {
    @ApiModelProperty("用户Id")
    private String userId;

    @ApiModelProperty("用户名称")
    private String userName;

    @ApiModelProperty("用户附近的朋友的ID列表")
    private List<String> friendIds;

}
