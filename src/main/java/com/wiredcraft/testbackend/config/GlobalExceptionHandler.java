package com.wiredcraft.testbackend.config;

import com.wiredcraft.testbackend.entity.Result;
import com.wiredcraft.testbackend.entity.ResultsCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.ConstraintViolationException;

/**
 * custom exception handling
 * author: yongchen
 * date: 2023/2/17
 */
@ControllerAdvice
@RestController
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Method parameter validation exception
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = ConstraintViolationException.class)
    public Result constraintViolationException(Exception e) {
        LOGGER.error("catch exception", e);
        if (e.getMessage() != null) {
            int index = e.getMessage().indexOf(":");
            return Result.error(ResultsCode.BAD_REQUEST.getCode(), index != -1 ? e.getMessage().substring(index + 1).trim() : e.getMessage());
        }
        return Result.error(ResultsCode.BAD_REQUEST.getCode(), null);
    }

    /**
     * Bean validation exception
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public Result notValidExceptionHandler(MethodArgumentNotValidException e) {
        LOGGER.error("catch exception", e);
        Result ret = new Result();
        ret.setCode(ResultsCode.BAD_REQUEST.getCode());
        if (e.getBindingResult() != null && !CollectionUtils.isEmpty(e.getBindingResult().getAllErrors())) {
            ret.setMessage(e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
        } else {
            ret.setMessage(e.getMessage());
        }
        return ret;
    }

    @ExceptionHandler(value = UsernameNotFoundException.class)
    public Result notFoundUserExceptionHandler(UsernameNotFoundException exception) {
        LOGGER.error("catch exception", exception);
        Result ret = new Result(ResultsCode.UNAUTHORIZED.getCode(), ResultsCode.UNAUTHORIZED.getMessage());
        if (exception.getMessage() != null && exception.getMessage().length() > 0) {
            ret.setMessage(exception.getMessage());
        }
        return ret;
    }

    @ExceptionHandler(value = AuthenticationException.class)
    public Result notAuthCredentialsExceptionHandler(AuthenticationException exception) {
        LOGGER.error("catch exception", exception);
        Result ret = new Result(ResultsCode.UNAUTHORIZED.getCode(), ResultsCode.UNAUTHORIZED.getMessage());
        if (exception.getMessage() != null && exception.getMessage().length() > 0) {
            ret.setMessage(exception.getMessage());
        }
        return ret;
    }

}
