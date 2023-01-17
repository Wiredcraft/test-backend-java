package com.wiredcraft.user.tiny.modules.ums.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *
 * @author yuao
 * @since 2023-01-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("user")
@ApiModel(value="user object", description="user entity")
public class User implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @ApiModelProperty(value = "user name")
    private String username;

    private String password;

    @ApiModelProperty(value = "icon")
    private String icon;

    @ApiModelProperty(value = "email")
    private String email;

    @ApiModelProperty(value = "nick name")
    private String nickName;

    @ApiModelProperty(value = "note")
    private String note;

    @ApiModelProperty(value = "user created date")
    private Date createTime;

    @ApiModelProperty(value = "last login time")
    private Date loginTime;

    @ApiModelProperty(value = "Account enabled status：0-> Disable; 1-> Enable")
    private Integer status;

    @ApiModelProperty(value = "user description")
    private String description;

    @ApiModelProperty(value = "user birthday")
    private Date birthday;

    @ApiModelProperty(value = "  user address")
    private String address;


}
