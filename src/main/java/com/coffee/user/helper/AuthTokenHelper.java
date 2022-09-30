package com.coffee.user.helper;

import com.coffee.user.response.info.UserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Token 相关处理
 *
 */
@Component
public class AuthTokenHelper {

    private static final Logger logger = LoggerFactory.getLogger(AuthTokenHelper.class);

    @Autowired
    private AuthJwtHelper authJwtHelper;


    /**
     * 登录令牌有效时间（单位：小时）
     */
    public static final int ACCESS_TOKEN_EXPIRE_HOUR = 2;


    /**
     * 生成令牌
     */
    public String generateAccessToken(UserInfo userInfo) {
        return authJwtHelper.generateToken(userInfo, ACCESS_TOKEN_EXPIRE_HOUR);
    }

}
