package com.coffee.user.response.info;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
@ApiModel("登录返回结果")
public class AuthLoginInfo {

    @ApiModelProperty("登录令牌")
    private String accessToken;

    @ApiModelProperty("用户Id")
    private String id;

    @ApiModelProperty("用户名")
    private String name;

    @ApiModelProperty("生日")
    private String dob;

    @ApiModelProperty("头像")
    private String address;

    @ApiModelProperty("邮箱")
    private String description;

}
