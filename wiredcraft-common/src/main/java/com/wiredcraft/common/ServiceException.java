package com.wiredcraft.common;

/**
 * @author jeremy.zhang
 * @date 2022-10-19
 */
public class ServiceException extends RuntimeException {
    private final int code;

    public ServiceException(int code, String msg) {
        super(msg);
        this.code = code;
    }
}
