package com.zhangyongxin.demo.common;

import lombok.ToString;

import java.io.Serializable;

/**
 * @Auther zhangyongxin
 * @date 2022/3/17 上午11:57
 */

@ToString
public class Result<T> implements Serializable {
    private int code;
    private String message;
    private T data;

    public Result() {
    }

    public Result(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public Result(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public Result(T data) {
        this.data = data;
        this.code = 200;
        this.message = "SUCCESS";
    }

    public int getCode() {
        return code;
    }

    public Result setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public Result setMessage(String message) {
        this.message = message;
        return this;
    }

    public Object getData() {
        return data;
    }

    public Result setData(T data) {
        this.data = data;
        return this;
    }
}
