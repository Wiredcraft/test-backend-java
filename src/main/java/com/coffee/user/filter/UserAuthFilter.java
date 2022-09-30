package com.coffee.user.filter;


import com.coffee.user.context.AuthenticationUserContextHolder;
import com.coffee.user.enums.ErrorCodeEnum;
import com.coffee.user.helper.AuthJwtHelper;
import com.coffee.user.response.info.UserInfo;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * filter拦截url请求
 * /login_user/*拦截并对token进行校验
 */
@Component
public class UserAuthFilter implements Filter {

    private static Logger logger = LoggerFactory.getLogger(UserAuthFilter.class);

    /**
     * 校验头文件
     */
    public static final String ACCESS_TOKEN = "access-token";

    @Autowired
    private AuthJwtHelper authJwtHelper;

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    @Transactional
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        ObjectMapper objectMapper = new ObjectMapper();
        HttpServletRequest req = (HttpServletRequest) request;
        String accessToken = req.getHeader(ACCESS_TOKEN);
        String requestUrl = req.getRequestURI();
        if (StringUtils.isBlank(accessToken)) {
            logger.warn("请求未携带token信息,url:{}", req.getRequestURI());
            response.getWriter().write(objectMapper.writeValueAsString(ErrorCodeEnum.USER_NOT_LOGIN));
            return;
        }

        UserInfo userInfo = authJwtHelper.extractToken(accessToken);
        if (userInfo == null) {
            // 判断是否是token过期
            if (authJwtHelper.isTokenExpired(accessToken)) {
                logger.warn("token信息已经过期,token:{},url:{}", accessToken, requestUrl);
                response.getWriter().write(objectMapper.writeValueAsString(ErrorCodeEnum.TOKEN_EXPIRED));
            } else {
                logger.warn("token信息解析错误,token:{},url:{}", accessToken, requestUrl);
                response.getWriter().write(objectMapper.writeValueAsString(ErrorCodeEnum.TOKEN_ANALYZING_EXCEPTION));
            }
            return;
        }

        try {
            AuthenticationUserContextHolder.setUserInfo(userInfo);
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            logger.error("auth exception msg:{},error:{},url:{}", e, e.getMessage(), requestUrl);
        } finally {
            AuthenticationUserContextHolder.clearContext();
        }
    }

    @Override
    public void destroy() {

    }

}
