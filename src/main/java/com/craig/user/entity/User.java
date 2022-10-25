package com.craig.user.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName("t_user")
public class User {
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * name
     */
    private String name;

    /**
     * date of birth
     */
    private Date dob;

    /**
     * user address
     */
    private String address;

    /**
     * description
     */
    private String description;

    /**
     * created time
     */
    private Date createdAt;

    /**
     * updated time
     */
    private Date updatedAt;

    /**
     * is deleted
     */
    private Boolean deleted;

    /**
     * salt + password in MD5
     */
    private String password;
    
}
