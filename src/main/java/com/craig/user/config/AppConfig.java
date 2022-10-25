package com.craig.user.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.craig.user.filter.LogFilter;

@Configuration
public class AppConfig {
    // @Bean
    // public LogFilter logFilter() {
    //     return new LogFilter();
    // }
    @Bean
    public FilterRegistrationBean<LogFilter> loggingFilter(){
        FilterRegistrationBean<LogFilter> registrationBean 
          = new FilterRegistrationBean<>();
        LogFilter logFilter = new LogFilter();
        registrationBean.setFilter(logFilter);
        registrationBean.addUrlPatterns("/users/*"); //only log the user APIs
        registrationBean.setOrder(logFilter.getOrder());
            
        return registrationBean;    
    }
}
