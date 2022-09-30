package com.coffee.user.configuration;

import com.coffee.user.filter.UserAuthFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 注册filter
 * bean名称尽量不和业务加载bean名称相同
 *
 */
@Configuration
public class AuthFilterRegisterConfig {

    @Autowired
    private UserAuthFilter userAuthFilter;

    /**
     * 注册买家拦截器
     */
    @Bean
    public FilterRegistrationBean buyerFilterBean() {
        //过滤器注册类
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(userAuthFilter);
        registration.setOrder(Integer.MIN_VALUE);
        //需要过滤的接口
        registration.addUrlPatterns("/login_user/*");
        return registration;
    }


}
