package com.coffee.user.response.info;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString(callSuper = true)
@ApiModel("用户信息")
public class UserInfo {
    @ApiModelProperty("用户Id")
    private String id;

    @ApiModelProperty("用户名")
    private String name;

    @ApiModelProperty("生日")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone="GMT+8")
    private Date dob;

    @ApiModelProperty("头像")
    private String address;

    @ApiModelProperty("邮箱")
    private String description;
}
