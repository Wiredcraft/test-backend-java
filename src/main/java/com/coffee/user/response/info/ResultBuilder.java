package com.coffee.user.response.info;

import com.coffee.user.response.Result;

public class ResultBuilder {

    public static <T> Result<T> succResult(T value) {
        Result<T> result = new Result();
        result.setData(value);
        result.setStatusCode(Result.SUCCESS_STATUS_CODE);
        result.setComments(Result.SUCCESS_MESSAGE);
        return result;
    }

    public static <T> Result<T> failResult(int errcode, String errmsg) {
        Result<T> result = new Result();
        result.setStatusCode(errcode);
        result.setComments(errmsg);
        return result;
    }
}
