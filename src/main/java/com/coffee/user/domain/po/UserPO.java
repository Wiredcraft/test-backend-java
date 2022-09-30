package com.coffee.user.domain.po;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UserPO extends BasePO {
    /**
     * 用户姓名
     */
    private String name;
    /**
     * 生日
     */
    private Date dob;

    /**
     * 密码
     */
    private String password;

    /**
     * 住址
     */
    private String address;
    /**
     * 其他描述
     */
    private String description;
}
