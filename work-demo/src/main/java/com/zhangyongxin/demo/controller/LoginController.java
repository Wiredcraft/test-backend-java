package com.zhangyongxin.demo.controller;

import com.zhangyongxin.demo.common.Result;
import com.zhangyongxin.demo.common.ResultGenerator;
import com.zhangyongxin.demo.controller.base.BaseController;
import com.zhangyongxin.demo.model.user.LoginUserParam;
import com.zhangyongxin.demo.model.user.UserInfo;
import com.zhangyongxin.demo.service.user.UserInfoService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static com.zhangyongxin.demo.common.Constant.SESSION_USER;

/**
 * 用户身份认证
 *
 * @Auther zhangyongxin
 * @date 2022/3/17 下午5:02
 */
@RestController
@RequestMapping("")
@Slf4j
public class LoginController extends BaseController {
    @Resource
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserInfoService userInfoService;

    @PostMapping("/login")
    @ApiOperation(value = "用户登录", notes = "用户登录")
    public Result<UserInfo> login(@RequestBody LoginUserParam loginUserParam, HttpServletRequest request) {
        UserInfo userInfo = new UserInfo();
        if (null != authenticationManager) {
            try {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(loginUserParam.getUsername(), loginUserParam.getPassword());
                authenticationManager.authenticate(authentication);
                HttpSession session = request.getSession();
                userInfo = userInfoService.findByName(loginUserParam.getUsername());
                userInfo.setPassword("****");
                session.setAttribute(SESSION_USER, userInfo);
            } catch (BadCredentialsException e) {
                return ResultGenerator.genFailResult("用户名或密码错误");
            }
        }
        return ResultGenerator.genSuccessResult(userInfo);
    }

    @PostMapping("/doLogout")
    @ApiOperation(value = "用户登出", notes = "用户登出")
    public Result<Boolean> logout(HttpServletRequest request) {
        this.logout();
        return ResultGenerator.genSuccessResult(true);
    }


}
