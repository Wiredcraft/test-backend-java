package com.craig.user.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtTokenUtil {

    @Value("${security.jwt.secret}")
    private String secretKey;

    @Value("${security.jwt.expiration}")
    private Integer expiration;


    /**
     * generateToken from userName
     *
     * @param userName   user name
     * @param userId user id
     * @return token
     */
    public String generateToken(String userName, Long userId) {
        byte[] jwtSecretKey = DatatypeConverter.parseBase64Binary(secretKey);

        Map<String, Object> claimsMap = new HashMap<>();
        claimsMap.put("userId", userId);
        claimsMap.put("userName", userName);

        String token = Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .signWith(Keys.hmacShaKeyFor(jwtSecretKey), SignatureAlgorithm.HS256)
                .setSubject(userName)
                .setIssuer("System")
                .setIssuedAt(new Date())
                .setAudience(userName)
                .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000))
                .setClaims(claimsMap)
                .compact();
        return token;
    }

    /**
     * validate token
     * @param token token
     * @return boolean
     */
    public boolean validate(String token) {
        try {
            getTokenBody(token);
            return true;
        } catch (ExpiredJwtException e) {
            log.warn("Request to parse expired JWT : {} failed : {}", token, e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.warn("Request to parse unsupported JWT : {} failed : {}", token, e.getMessage());
        } catch (MalformedJwtException e) {
            log.warn("Request to parse invalid JWT : {} failed : {}", token, e.getMessage());
        } catch (IllegalArgumentException e) {
            log.warn("Request to parse empty or null JWT : {} failed : {}", token, e.getMessage());
        }
        return false;
    }

    // /**
    //  * get user info from token
    //  *
    //  * @param token token
    //  * @return authentication
    //  */
    // public Authentication getAuthentication(String token) {
    //     Claims claims = getTokenBody(token);
    //     String userName = claims.getSubject();

    //     return new UsernamePasswordAuthenticationToken(userName, token, null);

    // }

    private Claims getTokenBody(String token) {
        byte[] jwtSecretKey = DatatypeConverter.parseBase64Binary(secretKey);
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(jwtSecretKey))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * get user id from token
     * @param token
     * @return user id
     */
    public Long getUserId(String token) {
        Claims claims = getTokenBody(token);
        return claims.get("userId", Long.class);
    }
}
