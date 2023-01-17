package com.wiredcraft.user.tiny.common.api;

/**
 *
 * @author yuao
 * @since 2023-01-14
 */
public enum ResultCode implements IErrorCode {
    SUCCESS(200, "操作成功"),
    FAILED(500, "操作失败"),
    VALIDATE_FAILED(404, "参数检验失败"),
    UNAUTHORIZED(401, "暂未登录或token已经过期"),
    FORBIDDEN(403, "没有相关权限"),

    NO_EXIST(404, "该用户不存在"),

    REPEAT_FOLLOW(403,"重复关注"),

    FOLLOW_LIMIT(403, "关注数超过上限");
    private long code;
    private String message;

    private ResultCode(long code, String message) {
        this.code = code;
        this.message = message;
    }

    public long getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
