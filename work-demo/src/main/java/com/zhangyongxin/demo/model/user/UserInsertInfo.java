package com.zhangyongxin.demo.model.user;

import lombok.Data;

import java.util.Date;

/**
 * 用户插入对象
 * @author zhangyongxin
 * @date 2022/3/20 12:31
 */
@Data
public class UserInsertInfo {
    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 姓名
     */
    private String name;

    /**
     * 生日
     */
    private Date dob;

    /**
     * 地址
     */
    private String address;

    /**
     * 描述
     */
    private String description;
}
