package com.craig.user.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.craig.user.entity.User;
import com.craig.user.model.LoginModel;
import com.craig.user.model.LoginResultModel;
import com.craig.user.repository.UserRepository;
import com.craig.user.service.AuthService;
import com.craig.user.util.JwtTokenUtil;
import com.craig.user.util.PasswordUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    public LoginResultModel authenticate(LoginModel login) {
        User existUser = userRepository.getByName(login.getUsername());
        if (existUser == null) {
            return null;
        }

        try {
            boolean passwordPass = PasswordUtil.validate(login.getPassword(), existUser.getPassword());
            if (!passwordPass) {
                return null;
            }
            LoginResultModel result = new LoginResultModel();
            String token = jwtTokenUtil.generateToken(existUser.getName(), existUser.getId());
            result.setToken(token);
            return result;
        } catch (Exception e) {
            log.error("validate password failed", e);
            throw new RuntimeException(e);
        }
    }

}
