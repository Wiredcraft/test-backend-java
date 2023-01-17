package com.wiredcraft.user.tiny.modules.ums.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

/**
*
 */
@Getter
@Setter
public class UpdateAdminPasswordParam {
    @NotEmpty
    @ApiModelProperty(value = "user name", required = true)
    private String username;
    @NotEmpty
    @ApiModelProperty(value = "old Password", required = true)
    private String oldPassword;
    @NotEmpty
    @ApiModelProperty(value = "new Password", required = true)
    private String newPassword;
}
