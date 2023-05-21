package com.test.demo.security;

import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.demo.utils.JWTUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

/**
 * The type Jwt interceptor, if the quest without token will reject directly
 * then we will validate the token using JWT
 *
 * @author zhangrucheng on 2023/5/21
 */
@Slf4j
public class JWTInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String token = request.getParameter("token");

        HashMap<String, Object> map = new HashMap<>();
        try {
            DecodedJWT verify = JWTUtils.verify(token);
            return true;
        } catch (SignatureVerificationException e) {
            log.error("无效签名" + e.toString());
            map.put("msg", "无效签名");
        } catch (AlgorithmMismatchException e) {
            map.put("msg", "算法不一致");
        } catch (Exception e) {
            log.error("token invalid");
            map.put("msg", "Token无效");
        }
        map.put("state", false);
        // 把Map转换为JSON响应
        String json = new ObjectMapper().writeValueAsString(map);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().println(json);
        return false;
    }
}
