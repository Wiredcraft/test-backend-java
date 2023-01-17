package com.wiredcraft.user.tiny.common.exception;


import com.wiredcraft.user.tiny.common.api.IErrorCode;

/**
 *
 * @author yuao
 * @since 2023-01-14
 */
public class Asserts {
    public static void fail(String message) {
        throw new ApiException(message);
    }

    public static void fail(IErrorCode errorCode) {
        throw new ApiException(errorCode);
    }
}
