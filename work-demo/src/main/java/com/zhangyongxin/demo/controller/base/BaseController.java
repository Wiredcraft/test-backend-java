package com.zhangyongxin.demo.controller.base;

import com.zhangyongxin.demo.common.DemoThreadCache;

/**
 * 登录信息相关
 *
 * @Auther zhangyongxin
 * @date 2022/3/17 下午3:11
 */
public class BaseController {
    /**
     * 获取登录用户username
     *
     * @return
     */
    protected String loginUserId() {
        String loginUsername = "notLoginUserId";
        if (null != DemoThreadCache.getUserName()) {
            loginUsername = DemoThreadCache.getUserName();
        }
        return loginUsername;
    }
}
