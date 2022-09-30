package com.coffee.user.controller;

import com.coffee.user.annotation.RestApi;
import com.coffee.user.context.AuthenticationUserContextHolder;
import com.coffee.user.response.info.UserInfo;
import com.coffee.user.request.UserUpdateParam;
import com.coffee.user.response.Result;
import com.coffee.user.response.info.ResultBuilder;
import com.coffee.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


/**
 * User信息变更实现
 */
@RestController
@Slf4j
@Api(value = "用户信息变更相关API", description = "用户信息变更相关API", tags = {"UserController"})
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/login_user/v1/user/update", method = RequestMethod.POST, consumes = APPLICATION_JSON_VALUE)
    @RestApi
    @ApiOperation(value = "更新用户信息", notes = "更新用户信息")
    public Result<Boolean> updateUserInfos(@RequestBody UserUpdateParam updateParam) {
        Boolean rt = userService.update(updateParam);
        return ResultBuilder.succResult(rt);
    }

    @RequestMapping(value = "/login_user/v1/user/disable", method = RequestMethod.PUT, consumes = APPLICATION_JSON_VALUE)
    @RestApi
    @ApiOperation(value = "用户注销", notes = "用户注销")
    public Result<Boolean> disableUser() {
        Boolean rt = userService.delete(AuthenticationUserContextHolder.getCurrentUserId());
        return ResultBuilder.succResult(rt);
    }

    @RequestMapping(value = "/login_user/v1/user/info", method = RequestMethod.GET, consumes = APPLICATION_JSON_VALUE)
    @RestApi
    @ApiOperation(value = "获取用户信息", notes = "获取用户信息")
    public Result<UserInfo> getUserInfo() {
        UserInfo userInfo = userService.findOne(AuthenticationUserContextHolder.getCurrentUserId());
        return ResultBuilder.succResult(userInfo);
    }

}
