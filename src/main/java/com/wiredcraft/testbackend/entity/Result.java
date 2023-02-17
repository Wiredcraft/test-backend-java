package com.wiredcraft.testbackend.entity;

import java.io.Serializable;

/**
 * description: response result
 * author: yongchen
 * date: 2023/2/17
 */
public class Result<T> implements Serializable {
    /**
     * response code, 200 is success
     */
    private int code;
    /**
     * response message
     */
    private String message;
    /**
     * response data
     */
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

    public static Result success() {
        return new Result(ResultsCode.SUCCESS.getCode(), ResultsCode.SUCCESS.getMessage(), null);
    }

    public static <T> Result<T> success(T data) {
        return new Result<T>(ResultsCode.SUCCESS.getCode(), ResultsCode.SUCCESS.getMessage(), data);
    }

    public static <T> Result<T> error() {
        return new Result<T>(ResultsCode.FAIL.getCode(), ResultsCode.FAIL.getMessage(), null);
    }

    public static <T> Result<T> error(String mesage) {
        return new Result<T>(ResultsCode.FAIL.getCode(), mesage, null);
    }

    public static <T> Result<T> error(int code, String mesage) {
        return new Result<T>(code, mesage, null);
    }

    public static <T> Result<T> error(ResultsCode resultsCode) {
        return new Result<T>(resultsCode.getCode(), resultsCode.getMessage(), null);
    }

    public static <T> Result<T> error(String mesage, ResultsCode resultsCode) {
        return new Result<T>(resultsCode.getCode(), mesage, null);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
