package com.coffee.user;

import org.springframework.boot.SpringApplication;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@ComponentScan(basePackages = {"com.coffee.user"})
@MapperScan(basePackages = {"com.coffee.user.dao.mapper"})
@EnableConfigurationProperties
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class UserApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }

}
