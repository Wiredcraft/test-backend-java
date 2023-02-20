package com.wiredcraft.testbackend.config;

import com.alibaba.fastjson.JSON;
import com.wiredcraft.testbackend.entity.Result;
import com.wiredcraft.testbackend.entity.ResultsCode;
import com.wiredcraft.testbackend.service.JwtUserDetailsService;
import com.wiredcraft.testbackend.util.JwtTokenUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Check whether the request has a valid JWT token
 * author: yongchen
 * date: 2023/2/19
 */
@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtRequestFilter.class);

    private static final String BEARER = "Bearer_";

    private static final String AUTHORIZATION_HEADER = "Authorization";

    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String header = request.getHeader(AUTHORIZATION_HEADER);
            String username = null;
            String token = null;
            // JWT Token is in the form "Bearer token". Remove Bearer word and get only the Token
            if (header != null && header.startsWith(BEARER)) {
                token = header.substring(BEARER.length());
                username = jwtTokenUtil.getUsernameFromToken(token);
            }

            // 拿到token后验证
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(username);
                // 如果token有效，配置Spring Security授权
                if (jwtTokenUtil.validateToken(token, userDetails)) {
                    System.out.println(username + " token验证通过");
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    //当前用户已经授权，授权认证信息传递给Spring Security配置.
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException e) {
            LOGGER.error("Token已过期: {} ", e);
            Result result = Result.error(ResultsCode.EXPIRED_TOKEN.getCode(), ResultsCode.EXPIRED_TOKEN.getMessage());
            response.getWriter().print(JSON.toJSONString(result));
        } catch (UnsupportedJwtException | MalformedJwtException e) {
            LOGGER.error("Token格式错误: {} ", e);
            Result result = Result.error(ResultsCode.ILLEGAL_FORMAT_TOKEN.getCode(), ResultsCode.ILLEGAL_FORMAT_TOKEN.getMessage());
            response.getWriter().print(JSON.toJSONString(result));
        } catch (Exception e) {
            throw e;
        }
    }
}
