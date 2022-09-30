package com.coffee.user.exception;

import com.coffee.user.enums.ErrorCodeEnum;
import lombok.Data;

@Data
public class BizException extends RuntimeException {
    private String errorCode;

    public BizException(String errorCode, String errorMsg) {
        super(errorMsg);
        this.errorCode = errorCode;
    }

    public BizException(String message) {
        super(message);
    }

    public BizException(ErrorCodeEnum errorCode) {
        super(errorCode.getErrorMsg());
        this.errorCode = errorCode.getErrorCode();
    }
}
