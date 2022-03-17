package com.zhangyongxin.demo.config;

import com.zhangyongxin.demo.common.DemoThreadCache;
import com.zhangyongxin.demo.model.UserInfo;
import org.apache.http.auth.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;

import static com.zhangyongxin.demo.common.Constant.SESSION_USER;

/**
 * 用户登录限制，使用session
 *
 * @Auther zhangyongxin
 * @date 2022/3/17 下午2:07
 */
@Component
public class UserLoginInterceptor extends HandlerInterceptorAdapter {

    public List<String> getExcludePath() {
        return Arrays.asList("/login");
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        UserInfo userInfo = (UserInfo) session.getAttribute(SESSION_USER);
        if (null == userInfo) {
            throw new AuthenticationException("对不起，您没有访问权限");
        }
        DemoThreadCache.setUserName(userInfo.getUsername());
        return super.preHandle(request, response, handler);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        super.afterCompletion(request, response, handler, ex);
        DemoThreadCache.clearAll();
    }
}
