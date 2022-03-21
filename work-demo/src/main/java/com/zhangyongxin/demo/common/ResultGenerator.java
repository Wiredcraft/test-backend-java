package com.zhangyongxin.demo.common;

import lombok.experimental.UtilityClass;

/**
 * @Auther zhangyongxin
 * @date 2022/3/17 下午12:25
 */
@UtilityClass
public class ResultGenerator {

    public int successCode = 200;
    public String successMsg = "SUCCESS";
    public int failCode = 400;
    public String failMsg = "ERROR";

    public <T> Result<T> genFailResult() {
        return new Result<T>(failCode, failMsg);
    }

    public <T> Result<T> genFailResult(String failMsg) {
        return new Result<T>(failCode, failMsg);
    }

    public <T> Result<T> genSuccessResult() {
        return new Result<T>(successCode, successMsg);
    }

    public <T> Result<T> genSuccessResult(T object) {
        return new Result<T>(successCode, successMsg, object);
    }
}
