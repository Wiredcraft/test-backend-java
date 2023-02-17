package com.wiredcraft.testbackend.config;

import com.wiredcraft.testbackend.entity.Result;
import com.wiredcraft.testbackend.entity.ResultsCode;
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

    /**
     * Method parameter validation exception
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = ConstraintViolationException.class)
    public Result constraintViolationException(Exception e) {
        if (e.getMessage() != null) {
            int index = e.getMessage().indexOf(":");
            return Result.error(ResultsCode.BAD_REQUEST.getCode(),
                    index != -1 ? e.getMessage().substring(index + 1).trim() : e.getMessage());
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
    public Result notValidExceptionHandler(MethodArgumentNotValidException e) throws Exception {
        Result ret = new Result();
        ret.setCode(ResultsCode.BAD_REQUEST.getCode());
        if (e.getBindingResult() != null && !CollectionUtils.isEmpty(e.getBindingResult().getAllErrors())) {
            ret.setMessage(e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
        } else {
            ret.setMessage(e.getMessage());
        }
        return ret;
    }

}
