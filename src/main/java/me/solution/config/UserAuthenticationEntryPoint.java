package me.solution.config;

import lombok.SneakyThrows;
import me.solution.utils.LoginUtils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * spring-security auth entry point
 *
 * @author davincix
 * @since 2023/5/22 13:35
 */
@Component
public class UserAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @SneakyThrows
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) {
        LoginUtils.writeFailMsg("登录检测异常，请检查登录信息..." + "," + e.toString(), response);
    }
}
