package com.coffee.user.request;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * UserCreateParam
 *
 */
@Setter
@Getter
@ToString
public class UserCreateParam {

    /**
     * 手机号
     */
    private String cellphone;

    /**
     * 昵称
     */
    private String name;

    /**
     * 密码
     */
    private String password;

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


//    @Override
//    public void checkParam() {
//        ParamChecker.notNull(bizCode, "bizCode为必填");
//        ParamChecker.notNull(status, "status为必填");
//        ParamChecker.expectTrue(StringUtils.isNotBlank(cellphone) || StringUtils.isNotBlank(email), "邮箱或手机号其一必填!");
//    }
}
