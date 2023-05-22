package me.solution.model.reqresp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.solution.enums.ResultCodeEnum;
import me.solution.exception.BizException;
import org.springframework.util.StringUtils;

/**
 * the result for all endpoint response
 *
 * @author davincix
 * @since 2023/5/22 10:58
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultResp<T> {
    /**
     * 0: the result is normal
     * not 0: biz code
     */
    private String code;

    /**
     * result msg
     */
    private String message;

    /**
     * the data to respond
     */
    private T data;

    /*** customized ***/
    public static <T> ResultResp<T> create(String code, String message, T data) {
        return new ResultResp<>(code, message, data);
    }

    public static <T> ResultResp<T> create(String code, String message) {
        return new ResultResp<>(code, message, null);
    }

    public static <T> ResultResp<T> success(String message, T data) {
        return create(ResultCodeEnum.SUCCESS.getCode(), message, data);
    }

    /*** success ***/
    public static <T> ResultResp<T> successData(T data) {
        return success(ResultCodeEnum.SUCCESS.getMsg(), data);
    }

    public static <T> ResultResp<T> successMessage(String message) {
        return success(message, null);
    }

    public static <T> ResultResp<T> success() {
        return successMessage(ResultCodeEnum.SUCCESS.getMsg());
    }

    /*** error ***/
    public static <T> ResultResp<T> errorData(String message, T data) {
        return create(ResultCodeEnum.INTERNAL_EXCEPTION.getCode(), message, data);
    }

    public static <T> ResultResp<T> errorData(T data) {
        return errorData(null, data);
    }

    public static <T> ResultResp<T> errorMessage(String message) {
        return errorData(message, null);
    }

    public static <T> ResultResp<T> error() {
        return errorMessage(null);
    }

    public static <T> ResultResp<T> error(Error e) {
        return e == null ? null : create(ResultCodeEnum.INTERNAL_EXCEPTION.getCode(), e.getMessage());
    }

    public static <T> ResultResp<T> error(Exception e) {
        if (e == null) {
            return null;
        }

        String code = ResultCodeEnum.INTERNAL_EXCEPTION.getCode();
        String msg = e.getMessage();
        if (e instanceof NullPointerException) {
            code = ResultCodeEnum.NULL_POINTER_EXCEPTION.getCode();
            msg = ResultCodeEnum.NULL_POINTER_EXCEPTION.getMsg();
        }
        return create(code, msg);
    }

//    public static <T> ResultResp<T> error(Exception e) {
//        if (e == null) {
//            return null;
//        }
//
//        String code = ResultCodeEnum.INTERNAL_EXCEPTION.getCode();
//        String msg = e.getMessage();
//        if (e instanceof NullPointerException) {
//            code = ResultCodeEnum.NULL_POINTER_EXCEPTION.getCode();
//            msg = ResultCodeEnum.NULL_POINTER_EXCEPTION.getMsg();
//        }
//        return create(code, msg);
//    }

    public static <T> ResultResp<T> error(BizException e) {
        if (e == null) {
            return null;
        }

        String code = StringUtils.isEmpty(e.getCode()) ? ResultCodeEnum.INTERNAL_EXCEPTION.getCode() : e.getCode();
        return create(code, e.getMessage());
    }
}
