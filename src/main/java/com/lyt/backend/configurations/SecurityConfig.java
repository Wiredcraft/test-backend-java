package com.lyt.backend.configurations;

import com.lyt.backend.service.LoginInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private LoginInfoService loginInfoService;

    @Autowired
    private PasswordEncoder encoder;


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(loginInfoService).passwordEncoder(encoder);
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/v1/register")
                .antMatchers("/health")
                .antMatchers("/v1/user/*")
                .antMatchers("/swagger-ui/**")
                .antMatchers("/swagger-resources/**")

                .antMatchers("/v3/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/v1/user").hasRole("USER")
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic()
                .and()
                .csrf().disable();
    }
}
