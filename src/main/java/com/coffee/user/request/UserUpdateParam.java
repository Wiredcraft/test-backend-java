package com.coffee.user.request;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UserUpdateParam {
    /**
     * id
     */
    private String id;

    /**
     * 生日
     */
    private Date dob;

    /**
     * 头像
     */
    private String address;

    /**
     * 邮箱
     */
    private String description;
}
