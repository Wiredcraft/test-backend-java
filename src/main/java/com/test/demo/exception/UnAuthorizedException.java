package com.test.demo.exception;

/**
 * @author zhangrucheng on 2023/5/23
 */
public class UnAuthorizedException extends RuntimeException {

    public UnAuthorizedException(String message) {
        super(message);
    }
}
