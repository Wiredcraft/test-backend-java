package me.solution.service;

import me.solution.model.reqresp.LoginReq;
import org.springframework.stereotype.Service;

/**
 * service for login/logout
 *
 * @author davincix
 * @since 2023/5/20 15:42
 */
@Service
public class LoginService {

    public void login(LoginReq req) {
        // invoke ProviderManager to check

        // generate jwt token, store the token to redis

        // jwt filter
        // 1. get token, resolve token to get user id
        // 2. get user from redis

        // put the login info into SecurityContextHolder
    }
}
