package com.zhangyongxin.demo.common;

import lombok.experimental.UtilityClass;

/**
 * @Auther zhangyongxin
 * @date 2022/3/17 下午12:25
 */
@UtilityClass
public class ResultGenerator {

    public int successCode=200;
    public String successMsg="SUCCESS";
    public int failCode =400;
    public String failMsg ="ERROR";

    public Result genFailResult(){
        return new Result(failCode,failMsg);
    }
    public Result genFailResult(String failMsg){
        return new Result(failCode,failMsg);
    }
    public Result genSuccessResult(){
        return new Result(successCode,successMsg);
    }
    public Result genSuccessResult(Object object){
        return new Result(successCode,successMsg,object);
    }
}
