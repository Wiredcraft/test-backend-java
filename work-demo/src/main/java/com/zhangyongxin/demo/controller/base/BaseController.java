package com.zhangyongxin.demo.controller.base;

import com.zhangyongxin.demo.model.user.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpSession;

import static com.zhangyongxin.demo.common.Constant.SESSION_USER;

/**
 * 登录信息相关
 *
 * @Auther zhangyongxin
 * @date 2022/3/17 下午3:11
 */
public class BaseController {
    @Autowired
    private HttpSession session;

    /**
     * 获取登录用户username
     *
     * @return
     */
    protected UserInfo getLoginUser() {
        UserInfo userInfo = (UserInfo) session.getAttribute(SESSION_USER);
        return userInfo;
    }

    /**
     * 登出
     */
    protected void logout(){
        session.removeAttribute(SESSION_USER);
    }
}
