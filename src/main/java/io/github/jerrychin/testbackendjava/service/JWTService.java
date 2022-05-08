package io.github.jerrychin.testbackendjava.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 * Provides JWT token generation and validation services.
 *
 * See <a href="https://github.com/auth0/java-jwt">auth0 java-jwt</a>
 */
@Component
public class JWTService {
    private static final String ISSUER = "test-backend-java";

    @Value("${security.access-token.expire-hours:24}")
    private Long expireHours;

    @Value("${security.access-token.secret:I&PYEkqGenob9H$zdwm7$TH6D9i@w&rc}")
    private String secret;

    public String generateToken(String account) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer(ISSUER)
                    .withSubject(account)
                    .withExpiresAt(Date.from(LocalDateTime.now().plus(expireHours, ChronoUnit.HOURS)
                            .atZone(ZoneId.systemDefault()).toInstant()))
                    .sign(algorithm);
        } catch (JWTCreationException ex){
            // Invalid Signing configuration / Couldn't convert Claims.

            throw new RuntimeException("generate token failed, account: " + account, ex);
        }
    }

    public DecodedJWT validateToken(String token) {
        return JWT.require(Algorithm.HMAC256(secret)).build().verify(token);
    }
}