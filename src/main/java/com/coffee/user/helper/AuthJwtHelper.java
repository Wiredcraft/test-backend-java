package com.coffee.user.helper;

import com.coffee.user.response.info.UserInfo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.DefaultJwsHeader;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.security.Key;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Jwt帮助类 实现方式一 使用 jwt
 */
@Component
@Getter
@Setter
public class AuthJwtHelper implements InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(AuthJwtHelper.class);

    private  Key signatureKey;

    private Key verifyKey;

    private long validity;

    private CompressionCodec compressionCodec = CompressionCodecs.DEFLATE;

    public void setKeyPair(KeyPair keyPair) {
        PrivateKey privateKey = keyPair.getPrivate();
        Assert.state(privateKey instanceof RSAPrivateKey, "KeyPair must be an RSA ");
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        Assert.state(publicKey != null, "KeyPair must be an RSA ");
        this.signatureKey = privateKey;
        this.verifyKey = publicKey;
    }

    /**
     *  jks文件生成命令
     *  keytool -genkeypair -alias mountainkey -keyalg RSA -keypass mountainpass -keystore mountain.jks -storepass mountainpass     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        ClassPathResource classPathResource = new ClassPathResource("mountain.jks");
        KeyStoreKeyFactory factory = new KeyStoreKeyFactory(classPathResource, "mountainpass".toCharArray());
        KeyPair keys = factory.getKeyPair("mountainkey", "mountainpass".toCharArray());
        this.setKeyPair(keys);
    }

    /**
     * 生成jwt token
     *
     * @param userInfo              用户授权信息
     * @param accessTokenExpireHour accessToken延长时间
     * @return token信息
     */
    public String generateToken(UserInfo userInfo, int accessTokenExpireHour) {
        Header header = new DefaultJwsHeader();
        header.setType("JWT");
        header.setContentType("JWT");
        header.setCompressionAlgorithm(compressionCodec.getAlgorithmName());
        Map<String, Object> claims = new HashMap<>(16);

        claims.put("userId", userInfo.getId());
        claims.put("userName", userInfo.getName());

        // accessToken的续签时间可以适当的往后延长一点
        // 因为filter过滤的时候，如果accessToken已经过期，那么从jwt拿出来的就是空对象
        Date expiredTime = new Date(new Date().getTime() + accessTokenExpireHour * 60 * 60L * 1000L);
        String token = Jwts.builder().
                setHeader((Map<String, Object>) header)
                .setClaims(claims)
                .setExpiration(expiredTime)
                .signWith(SignatureAlgorithm.RS512, signatureKey)
                .compressWith(compressionCodec)//压缩
                .compact();
        if (logger.isDebugEnabled()) {
            logger.debug("jwt token for userInfo:{},token:{}", userInfo, token);
        }
        return token;
    }

    /**
     * 获取授权用户信息
     *
     * @param token 解析token
     * @return 认证信息
     */
    public UserInfo extractToken(String token) {
        if (StringUtils.isBlank(token)) {
            return null;
        }
        // 获取认证信息，报错返回null，不影响filter处理
        try {
            return extractTokenNoCatch(token);
        } catch (ExpiredJwtException e) {
            // 如果是token过期异常，直接返回告诉业务端token过期
            logger.error("token expired msg:{}", e.getMessage());
        } catch (Exception err) {
            logger.error("extract token failure", err);
        }
        return null;
    }

    /**
     * 预判断token是否过期
     */
    public Boolean isTokenExpired(String token) {
        Assert.notNull(token, "token不能为空！");
        // 直接捕获过期异常
        try {
            extractTokenNoCatch(token);
        } catch (ExpiredJwtException e) {
            // 如果是token过期异常，直接返回告诉业务端token过期
            return true;
        } catch (Exception err) {
            return false;
        }
        return false;
    }

    /**
     * 获取授权用户信息
     */
    public UserInfo extractTokenNoCatch(String token) {
        Claims claims = Jwts.parser().setSigningKey(this.verifyKey).parseClaimsJws(token).getBody();
        Assert.notNull(claims, "token解析异常");

        UserInfo userInfo = new UserInfo();
        userInfo.setId(claims.get("userId").toString());
        userInfo.setName(claims.get("userName").toString());
        return userInfo;
    }


}
