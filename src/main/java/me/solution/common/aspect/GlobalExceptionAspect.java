package me.solution.common.aspect;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.solution.common.annotations.exception.BizException;
import me.solution.common.enums.ResultCodeEnum;
import me.solution.model.reqresp.ResultResp;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * global exception aspect
 *
 * @author davincix
 * @since 2023/5/22 11:58
 */
@RequiredArgsConstructor
@RestControllerAdvice(value = {"me.solution.endpoint"})
@Component
@Slf4j
public class GlobalExceptionAspect {
    @ExceptionHandler(value = BizException.class)
    @ResponseBody
    public ResultResp<?> bizExceptionHandler(HttpServletRequest req, BizException e) {
        return ResultResp.error(e);
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResultResp<?> exceptionHandler(HttpServletRequest req, Exception e) {
        if (e instanceof NullPointerException) {
            return ResultResp.error(ResultCodeEnum.NULL_POINTER_EXCEPTION);
        }

        if (e instanceof BindException) {
            String code = ResultCodeEnum.ILLEGAL_PARAM.getCode();
            String msg = ((BindException) e).getBindingResult().getAllErrors().get(0).getDefaultMessage();
            return ResultResp.create(code, msg);
        }

        return ResultResp.error(e);
    }

    @ExceptionHandler(value = RuntimeException.class)
    @ResponseBody
    public ResultResp<?> exceptionHandler(HttpServletRequest req, RuntimeException e) {
        return ResultResp.error(e);
    }
}
