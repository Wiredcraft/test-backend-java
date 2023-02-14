package com.w.t.common.core.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class R<T> implements Serializable {

    private static final long serialVersionUID = 1l;

    public static final int SUCCESS =200;

    public static final int FAIL = 500;

    private int code;

    private String msg;

    private T data;

    private static <T> R<T> restResult(T data, int code, String msg) {
        R<T> apiResult = new R<T>();
        apiResult.setCode(code);
        apiResult.setData(data);
        apiResult.setMsg(msg);
        return apiResult;
    }

    public static <T> R<String> success() {
        return restResult("", SUCCESS, "");
    }

    public static <T> R<T> success(T data) {
        return restResult(data, SUCCESS, "");
    }

    public static <T> R<T> success(T data, String msg) {
        return restResult(data, SUCCESS, msg);
    }

    public static <T> R<String> fail() {
        return restResult("", FAIL, "");
    }

    public static <T> R<T> fail(T data) {
        return restResult(data, FAIL, null);
    }

    public static <T> R<T> fail(T data, String msg) {
        return restResult(data, FAIL, msg);
    }

}
