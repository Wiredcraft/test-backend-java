package me.solution.common.config;

import lombok.SneakyThrows;
import me.solution.common.enums.ResultCodeEnum;
import me.solution.common.utils.WebUtil;
import me.solution.model.reqresp.ResultResp;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * spring-security auth exception handler
 *
 * @author davincix
 * @since 2023/5/22 13:35
 */
@Component
public class UserAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @SneakyThrows
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) {
        ResultResp<Object> resp = ResultResp.error(ResultCodeEnum.UNAUTHORIZED);
        WebUtil.writeFailMsg(resp, response);
    }
}
