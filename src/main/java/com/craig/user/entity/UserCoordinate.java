package com.craig.user.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.craig.user.handler.GeometryPointTypeHandler;

import lombok.Data;

@Data
@TableName( value = "t_user_coordinate", autoResultMap = true)
public class UserCoordinate {
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * user id
     */
    private Long userId;

    /**
     * user lastest coordinate
     */
    @TableField(typeHandler = GeometryPointTypeHandler.class)
    private String lastCoord;

    /**
     * log time
     */
    private Date logTime;
}
