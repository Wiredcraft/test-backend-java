package com.wiredcraft.user.tiny.modules.ums.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;

/**
 *
 * @author yuao
 * @since 2023-01-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class UmsAdminLoginParam {
    @NotEmpty
    @ApiModelProperty(value = "username",required = true)
    private String username;
    @NotEmpty
    @ApiModelProperty(value = "password",required = true)
    private String password;
}
