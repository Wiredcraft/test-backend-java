package com.coffee.user.request;

import lombok.Getter;
import lombok.Setter;

/**
 * 注册用户实体类
 *

 */
@Getter
@Setter
public class UserRegisterParam {

    /**
     * 手机号
     */
    private String cellphone;

    /**
     * 用户名
     */
    private String name;

    /**
     * 密码
     */
    private String password;

    /**
     * 生日
     */
    private String dob;

    /**
     * 头像
     */
    private String address;

    /**
     * 邮箱
     */
    private String description;

    /**
     * 验证码
     */
    private String verifyCode;

//
//    @Override
//    public void checkParam() {
//        ParamChecker.notNull(bizCode, "业务端编码为必填项目");
//        ParamChecker.notNull(subBizCode, "二级业务端编码为必填项目");
//        ParamChecker.notBlank(cellphone, "cellphone为必填");
//        if (BooleanUtils.isNotTrue(whiteListFreeVerifyCodeCheck)) {
//            ParamChecker.notBlank(verifyCode, "验证码为必填项目");
//        }
//    }

}
