package com.zhangyongxin.demo.config;

import com.zhangyongxin.demo.handler.UserPermissionEvaluator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;

/**
 * 权限处理设置
 * @Auther zhangyongxin
 * @date 2022/3/17 上午11:51
 */
@Configuration
public class WebSecurityMethodConfig {

    @Bean
    public DefaultMethodSecurityExpressionHandler expressionHandler(UserPermissionEvaluator permissionEvaluator) {
        DefaultMethodSecurityExpressionHandler expressionHandler = new DefaultMethodSecurityExpressionHandler();
        expressionHandler.setPermissionEvaluator(permissionEvaluator);
        return expressionHandler;
    }

}
