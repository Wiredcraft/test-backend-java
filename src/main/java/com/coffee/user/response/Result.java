package com.coffee.user.response;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class Result<T> implements Serializable {
    public static String SUCCESS_MESSAGE = "成功";
    public static int SUCCESS_STATUS_CODE = 200;
    public static String FAIL_ERROR_CODE = "RE00000000000000";

    private int statusCode;

    private String errorCode;

    private String comments;

    private T data;

}
