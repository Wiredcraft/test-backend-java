package me.solution.aspect;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.solution.exception.BizException;
import me.solution.model.reqresp.ResultResp;
import org.springframework.stereotype.Component;
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
        return ResultResp.error(e);
    }

    @ExceptionHandler(value = RuntimeException.class)
    @ResponseBody
    public ResultResp<?> exceptionHandler(HttpServletRequest req, RuntimeException e) {
        return ResultResp.error(e);
    }
}
