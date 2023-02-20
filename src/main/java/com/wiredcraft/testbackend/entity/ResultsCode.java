package com.wiredcraft.testbackend.entity;

/**
 * description: http rsponse code
 * author: yongchen
 * date: 2023/2/17
 */
public enum ResultsCode {

    SUCCESS(200, "success"),

    FAIL(-1, "failed"),

    BAD_REQUEST(400, "Invalid parameter"),

    UNAUTHORIZED(401, "Unauthorized"),

    WRONG_NAME_PASS(402, "Wrong username or password"),

    EXPIRED_TOKEN(403, "Expired token"),

    ILLEGAL_FORMAT_TOKEN(404, "illegal format token"),

    SERVER_ERROR(500, "Server exception");

    private int code;
    private String message;

    ResultsCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
