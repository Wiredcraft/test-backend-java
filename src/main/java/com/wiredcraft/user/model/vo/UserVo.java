package com.wiredcraft.user.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;


/*
 * for convenience, we use only one view object to map all api param.
 * */
@Getter
@Setter
@ApiModel(description = "User View Object")
public class UserVo {

    @ApiModelProperty(required = false, value = "for update and query apis, it mustn't null")
    private Long id;

    @ApiModelProperty(required = false)
    private String name;

    @ApiModelProperty(required = false)
    private Date dob;

    @ApiModelProperty(required = false)
    private String address;

    @ApiModelProperty(required = false)
    private String description;

    @ApiModelProperty(required = false)
    private Date createdAt;

}
