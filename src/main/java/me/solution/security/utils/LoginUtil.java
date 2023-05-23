package me.solution.security.utils;

import lombok.extern.slf4j.Slf4j;
import me.solution.security.model.LoginUser;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * util for login user
 *
 * @author davincix
 * @since 2023/5/22 01:34
 */
@Slf4j
public abstract class LoginUtil {

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
