package com.w.t.common.security;

import com.alibaba.fastjson.JSON;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.w.t.common.core.constant.LocalStorageConstant;

import java.util.Date;

public class JwtUtil {

    private static final String SECRET = "DuoDuo/2019/01/30";

    private static long defaultTokenLifetime = 1000 * 60 * 60 * 24;

    public static String createToken(String tokenKey,Long userId) {
        UserDetail userDetail = new UserDetail(userId);
        return createToken(tokenKey,userDetail);
    }

    public static String createToken(String tokenKey,UserDetail userDetail) {
        //加密算法
        Algorithm algorithm = Algorithm.HMAC256(SECRET);
        //发布时间
        Date publishTime = new Date(System.currentTimeMillis());
        //过期时间
        Date expiredTime = new Date(System.currentTimeMillis() + defaultTokenLifetime);
        String token = JWT.create()
                .withIssuedAt(publishTime)
                .withExpiresAt(expiredTime)
                .withClaim(LocalStorageConstant.TOKEN_KEY, tokenKey)
                .withClaim(LocalStorageConstant.USER_DETAIL, JSON.toJSONString(userDetail))
                .sign(algorithm);
        return token;
    }

    public static UserDetail parseToken(String token) {
        //加密算法
        Algorithm algorithm = Algorithm.HMAC256(SECRET);
        try {
            DecodedJWT decodedJWT = JWT.require(algorithm).build().verify(token);
            return JSON.parseObject(decodedJWT.getClaim(LocalStorageConstant.USER_DETAIL).asString(), UserDetail.class);
        } catch (Exception e) {
            return null;
        }
    }

}
