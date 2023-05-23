package me.solution.common.config;

import me.solution.common.enums.ResultCodeEnum;
import me.solution.common.utils.WebUtil;
import me.solution.model.reqresp.ResultResp;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * spring security access denied exception handler
 *
 * @author davincix
 * @since 2023/5/22 13:36
 */
@Component
public class UserAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
        ResultResp<Object> resp = ResultResp.error(ResultCodeEnum.FORBIDDEN);
        WebUtil.writeFailMsg(resp, response);
    }
}
