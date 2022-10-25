package com.craig.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;

import com.baomidou.mybatisplus.autoconfigure.MybatisPlusAutoConfiguration;

@SpringBootApplication(exclude = { UserDetailsServiceAutoConfiguration.class, SecurityAutoConfiguration.class, MybatisPlusAutoConfiguration.class})
public class TestApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestApplication.class, args);
    }
}
