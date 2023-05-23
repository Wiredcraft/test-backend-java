package me.solution.common.filter;

import io.jsonwebtoken.Claims;
import me.solution.common.constants.AuthConstant;
import me.solution.model.transfer.LoginUser;
import me.solution.common.utils.JwtUtil;
import me.solution.common.utils.component.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

/**
 * Spring-Security filter for jwt token
 *
 * @author davincix
 * @since 2023/5/22 01:00
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    @Autowired
    private RedisUtil redisUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader(AuthConstant.TOKEN_HEADER);
        // if no token, let go
        if (!StringUtils.hasText(token)) {
            filterChain.doFilter(request, response);
            return;
        }

        // resolve userId from token
        String userId;
        try {
            Claims claims = JwtUtil.parseJWT(token);
            userId = claims.getSubject();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("illegal token");
        }

        // get user from redis
        String redisKey = "login:" + userId;
        LoginUser loginUser = (LoginUser) redisUtil.get(redisKey);
        Optional.ofNullable(loginUser)
                .orElseThrow(() -> new RuntimeException("please login"));

        // put into SecurityContextHolder
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        filterChain.doFilter(request, response);
    }
}
