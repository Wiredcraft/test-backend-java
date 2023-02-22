package com.wiredcraft.testbackend.config;

import com.alibaba.fastjson.JSON;
import com.wiredcraft.testbackend.entity.Result;
import com.wiredcraft.testbackend.entity.ResultsCode;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

/**
 * Process unauthenticated requests
 * author: yongchen
 * date: 2023/2/19
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException exception) throws IOException {
        Result result = Result.error(ResultsCode.UNAUTHORIZED.getCode(), exception.getMessage());
        response.getWriter().print(JSON.toJSONString(result));
    }
}
