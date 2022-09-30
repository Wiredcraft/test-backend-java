package com.coffee.user.request;

import lombok.Getter;
import lombok.Setter;

/**
 * AuthLoginParam
 *

 */
@Setter
@Getter
public class AuthLoginParam {
    /**
     * 用户名
     */
    private String name;

    /**
     * 密码
     */
    private String password;


//    @Override
//    public void checkParam() {
//        ParamChecker.notNull(bizCode, "bizCode为必填");
//        ParamChecker.notNull(subBizCode, "操作员所属二级业务端编码为必填项目");
//        ParamChecker.notNull(loginType, "loginType为必填");
//        // 根据不同登录类型校验参数
//        if (LoginTypeEnum.CELLPHONE_VERIFY_CODE == loginType) {
//            ParamChecker.notNull(cellphone, "cellphone为必填");
//            ParamChecker.notNull(verifyCode, "verifyCode为必填");
//        } else if (LoginTypeEnum.CELLPHONE_PASSWORD == loginType) {
//            ParamChecker.notNull(cellphone, "cellphone为必填");
//            ParamChecker.notNull(password, "password为必填");
//        } else if (LoginTypeEnum.USERNAME_PASSWORD == loginType) {
//            ParamChecker.notNull(username, "username为必填");
//            ParamChecker.notNull(password, "password为必填");
//        }  else {
//            throw new IllegalArgumentException("不支持的登陆类型");
//        }
//    }

}
