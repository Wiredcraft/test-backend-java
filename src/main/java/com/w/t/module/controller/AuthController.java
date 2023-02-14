package com.w.t.module.controller;

import com.w.t.module.entity.dto.LoginBody;
import com.w.t.module.entity.dto.RegisterBody;
import com.w.t.module.service.IUserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.w.t.common.core.response.R;
import com.w.t.common.util.storage.LocalStorageUtil;

/**
 * @author zhangxiang
 * @date 2023/02/09
 */
@Api(value = "鉴权管理")
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    IUserService userService;

    @PostMapping("/register")
    public R register(@RequestBody RegisterBody registerBody) {
        userService.registerUser(registerBody.getUsername(), registerBody.getPassword());
        return R.success();
    }

    @PostMapping("/login")
    public R<String> login(@RequestBody LoginBody loginBody) {
        String token = userService.login(loginBody.getUsername(), loginBody.getPassword());
        return R.success(token);
    }

    @GetMapping("/logout")
    public R logout() {
        Long userId = LocalStorageUtil.getCurrentUserId();
        userService.logout(userId);
        return R.success();
    }

}
