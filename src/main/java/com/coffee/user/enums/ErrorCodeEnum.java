package com.coffee.user.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 统一业务错误码
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorCodeEnum {

    ENTITY_NOT_FOUND("RE060615012000", "演示错误描述"),
    ILLEGAL_ARGUMENTS("RE060615012001", "参数错误"),
    USER_NOT_FOUND("RE060615012002", "用户不存在"),
    USER_NOT_LOGIN("RE060615012003", "用户未登陆"),
    TOKEN_ANALYZING_EXCEPTION("RE060615012004", "token信息解析错误"),
    TOKEN_EXPIRED("RE060615012005", "token过期"),
    REGISTER_ERROR("RE060615012006", "注册失败"),
    NO_NEED_VERIFY_CODE("RE060615012007", "一分钟内无需重复申请验证码"),
    USERNAME_PASSWORD_ERROR("RE060615012008", "用户名密码校验出错"),
    ;

    private String errorCode;
    private String errorMsg;

    ErrorCodeEnum(String errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

}
