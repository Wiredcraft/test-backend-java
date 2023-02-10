package com.wiredcraft.test.user.tool;

/**
 * 返回结果统一封装
 * 也可以方便统一异常处理
 */
public final class JsonResult<T> {

    private int code = 0;
    private String msg;
    private T data;

    private JsonResult(){
        this.data = null;
    }

    private JsonResult(int code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public static <T> JsonResult<T> success(){
        return new JsonResult<>();
    }

    public static <T> JsonResult<T> success(T data){
        JsonResult<T> result = success();
        result.setdata(data);
        return result;
    }

    public static <T> JsonResult<T> failure(int code, String msg){
        return  new JsonResult<>(code, msg);
    }

    public int getCode(){
        return code;
    }

    public JsonResult<T> setCode(int code){
        this.code = code;
        return this;
    }

    public String getMsg(){
        return msg;
    }

    public JsonResult<T> setMsg(String msg){
        this.msg = msg;
        return this;
    }

    public T getData(){
        return data;
    }

    public JsonResult<T> setdata(T data){
        this.data = data;
        return this;
    }

}
