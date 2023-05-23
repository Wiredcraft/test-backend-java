package me.solution.common.annotations.exception;

import lombok.Data;
import lombok.NoArgsConstructor;
import me.solution.common.enums.ResultCodeEnum;

/**
 * @author davincix
 * @since 2023/5/22 11:05
 */
@Data
@NoArgsConstructor
public class BizException extends RuntimeException {
    private String code;
    private String message;

    public BizException(String code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.message = message;
    }

    public BizException(String code, Throwable cause) {
        this(code, null, cause);
    }

    public BizException(String code, String message) {
        this(code, message, null);
    }

    public BizException(String code) {
        this(code, (String) null);
    }

    public BizException(ResultCodeEnum resultCodeEnum) {
        this(resultCodeEnum.getCode(), resultCodeEnum.getMsg());
    }
}
