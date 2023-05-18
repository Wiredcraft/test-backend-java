package com.jiang.test.backend.interceptor;

import com.jiang.test.backend.annotation.Authorized;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Slf4j
public class AuthorizationInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 检查是否有 @Authorized 注解
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            if (handlerMethod.hasMethodAnnotation(Authorized.class)) {
                // 进行授权检查逻辑
                if (!isAuthorized(request)) {
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    return false; // 返回 false，拦截请求
                }
            }
        }
        return true; // 允许请求继续处理
    }

    private boolean isAuthorized(HttpServletRequest request) {
        // 进行授权检查的逻辑，例如检查请求头、访问令牌等
        log.info("Authorization checking");
        return true;
    }
}
