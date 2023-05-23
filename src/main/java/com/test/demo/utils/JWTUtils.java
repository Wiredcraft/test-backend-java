package com.test.demo.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Map;

/**
 * The type Jwt utils.
 *
 * @author zhangrucheng on 2023/5/21
 */
public class JWTUtils {
    private static final String SING = "123test";


    /**
     * Get token string.
     *
     * @param map the map
     * @return the string
     */
    public static String getToken(Map<String, String> map) {
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.DATE, 7); // 默认过期时间 7天
        JWTCreator.Builder builder = JWT.create();
        // payload 设置
        map.forEach(builder::withClaim);
        // 生成Token 并返回
        try {
            return builder.withExpiresAt(instance.getTime()).sign(Algorithm.HMAC256(SING));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * verify Token
     *
     * @param token the token
     * @return DecodedJWT 可以用来获取用户信息
     */
    public static DecodedJWT verify(String token) throws JWTVerificationException {
        // 如果不抛出异常说明验证通过，否则验证失败
        try {
            return JWT.require(Algorithm.HMAC256(SING)).build().verify(token);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}
