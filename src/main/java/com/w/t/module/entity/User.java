package com.w.t.module.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.w.t.common.core.web.BaseEntity;
import lombok.Data;

/**
 * @author zhangxiang
 * @date 2023/02/09
 */
@Data
@TableName("account_user")
public class User extends BaseEntity {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String name;

    private String password;

    private String dob;

    private String address;

    private String description;

    private Double longitude;

    private Double latitude;

}
