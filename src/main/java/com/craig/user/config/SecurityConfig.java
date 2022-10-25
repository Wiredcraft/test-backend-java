package com.craig.user.config;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.craig.user.filter.JwtTokenFilter;

@EnableWebSecurity
public class SecurityConfig {
        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http, JwtTokenFilter jwtTokenFilter) throws Exception {
                // Enable CORS and disable CSRF
                http = http.cors().and().csrf().disable();

                // Set session management to stateless
                http = http
                        .sessionManagement()
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                        .and();
                // Set unauthorized requests exception handler
                http = http
                        .exceptionHandling()
                        .authenticationEntryPoint(
                                (request, response, ex) -> {
                                        response.sendError(
                                                        HttpServletResponse.SC_UNAUTHORIZED,
                                                        ex.getMessage());
                                })
                        .and();

                // Set permissions on endpoints
                http.authorizeRequests()
                        .antMatchers("/v3/api-docs/**", "/swagger-ui/**", "/favicon.ico", "/auth/**")
                        .permitAll()
                        .anyRequest()
                        .authenticated();
                        
                // Add JWT token filter
                http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);

                return http.build();
        }
}
