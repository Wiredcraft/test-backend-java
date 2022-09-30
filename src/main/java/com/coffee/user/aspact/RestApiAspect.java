package com.coffee.user.aspact;

import com.coffee.user.annotation.RestApi;
import com.coffee.user.exception.BizException;
import com.coffee.user.response.Result;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

@Aspect
@Component
public class RestApiAspect {

    private static Logger logger = LoggerFactory.getLogger(RestApiAspect.class);

    @Pointcut("@annotation(com.coffee.user.annotation.RestApi)")
    public void operationLogPointCut() {

    }

    @Around("operationLogPointCut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        // 获取方法的关键信息，类，包
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        RestApi sysLog = method.getAnnotation(RestApi.class);

        //请求的 类名、方法名
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = signature.getName();

        //请求的参数
        Object[] args = joinPoint.getArgs();
        if (sysLog != null) {
            //注解上的描述
            logger.info(
                    "time = {}, desc = {}, method = {}, className = {}, methodName = {}, list = {}",
                    dateFormat.format(new Date()),
                    sysLog.description(),
                    sysLog.method(),
                    className,
                    methodName,
                    Arrays.asList(args)
            );
        }
        Object response = null;
        try {
            response = joinPoint.proceed();
        } catch (BizException var4) {
            Result result = new Result();
            result.setErrorCode(var4.getErrorCode());
            result.setComments(var4.getMessage());
            return result;
        } catch (Exception var5) {
            logger.error(var5.getMessage(), var5);
            Result result = new Result();
            result.setErrorCode(Result.FAIL_ERROR_CODE);
            result.setComments(var5.getMessage());
            return result;
        }

        return response;
    }

}
