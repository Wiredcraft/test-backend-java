package com.coffee.user.controller;

import com.coffee.user.annotation.RestApi;
import com.coffee.user.request.AuthLoginParam;
import com.coffee.user.request.UserRegisterParam;
import com.coffee.user.response.Result;
import com.coffee.user.response.info.AuthLoginInfo;
import com.coffee.user.response.info.ResultBuilder;
import com.coffee.user.service.AuthenticationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Authentication的RPC服务提供者实现
 *
 * @author Mia
 * @since 2022/09/27
 */
@RestController
@Slf4j
@Api(value = "用户登陆相关API", description = "用户登陆相关API", tags = {"AuthenticationController"})
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @RequestMapping(value = "/user/v1/authentication/login", method = RequestMethod.POST, consumes = APPLICATION_JSON_VALUE)
    @RestApi
    @ApiOperation(value = "用户登录", notes = "用户登录")
    public Result<AuthLoginInfo> userLogin(@RequestBody AuthLoginParam param) {
        return ResultBuilder.succResult(authenticationService.login(param));
    }

    @RequestMapping(value = "/user/v1/authentication/register", method = RequestMethod.POST, consumes = APPLICATION_JSON_VALUE)
    @RestApi
    @ApiOperation(value = "用户注册", notes = "用户注册")
    public Result<Boolean> userRegister(@RequestBody UserRegisterParam param) {
        return ResultBuilder.succResult(authenticationService.register(param));
    }

}
