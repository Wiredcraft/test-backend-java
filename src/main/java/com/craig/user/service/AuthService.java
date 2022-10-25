package com.craig.user.service;

import com.craig.user.model.LoginModel;
import com.craig.user.model.LoginResultModel;

public interface AuthService {
    LoginResultModel authenticate(LoginModel login);
}
