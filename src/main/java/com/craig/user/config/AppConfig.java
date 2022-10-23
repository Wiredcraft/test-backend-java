package com.craig.user.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.craig.user.filter.LogFilter;

@Configuration
public class AppConfig {
    @Bean
    public LogFilter logFilter() {
        return new LogFilter();
    }
}
