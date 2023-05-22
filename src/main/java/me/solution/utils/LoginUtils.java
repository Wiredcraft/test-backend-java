package me.solution.utils;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import me.solution.enums.ResultCodeEnum;
import me.solution.model.reqresp.ResultResp;
import me.solution.model.transfer.LoginUser;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * util for login user
 *
 * @author davincix
 * @since 2023/5/22 01:34
 */
@Slf4j
public abstract class LoginUtils {

    public static void writeFailMsg(String message, HttpServletResponse response) throws IOException {
        ResultResp<Object> failure = ResultResp.create(ResultCodeEnum.ILLEGAL_TOKEN.getCode(), message);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.getWriter().write(JSON.toJSONString(failure));
    }

    public static LoginUser getLoginUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof LoginUser) {
            return (LoginUser) principal;
        }
        return null;
    }

    public static LoginUser getLoginUserRequireNonNull() {
        LoginUser loginUser = getLoginUser();
        if (loginUser == null) {
            throw new RuntimeException("please login");
        }
        return loginUser;
    }

    public static Long getUserId() {
        LoginUser loginUser = getLoginUser();
        if (loginUser == null) {
            return null;
        }
        return loginUser.getUser().getId();
    }

    public static Long getUserIdRequireNonNull() {
        LoginUser taLoginUser = getLoginUserRequireNonNull();
        return taLoginUser.getUser().getId();
    }
}
