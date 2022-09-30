package com.coffee.user.controller;

import com.coffee.user.annotation.RestApi;
import com.coffee.user.context.AuthenticationUserContextHolder;
import com.coffee.user.request.FriendCreateParam;
import com.coffee.user.response.Result;
import com.coffee.user.response.info.FriendInfo;
import com.coffee.user.response.info.ResultBuilder;
import com.coffee.user.service.FriendService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * UserFriend服务提供者实现
 *
 */
@RestController
@Slf4j
@Api(value = "用户朋友相关API", description = "用户朋友相关API", tags = {"UserFriendController"})
public class UserFriendController {

    @Autowired
    private FriendService friendService;

    @RequestMapping(value = "/login_user/v1/user/friend/create", method = RequestMethod.POST, consumes = APPLICATION_JSON_VALUE)
    @RestApi
    @ApiOperation(value = "指定用户新增朋友", notes = "指定用户新增朋友")
    public Result<Boolean> save(@RequestBody FriendCreateParam createParam) {
        Boolean rt = friendService.create(createParam);
        return ResultBuilder.succResult(rt);
    }

    @RequestMapping(value = "/login_user/v1/user/friend/remove/{id}", method = RequestMethod.DELETE, consumes = APPLICATION_JSON_VALUE)
    @RestApi
    @ApiOperation(value = "逻辑删用户朋友信息", notes = "逻辑删用户朋友信息")
    public Result<Boolean> remove(@PathVariable String id) {
        Boolean rt = friendService.delete(id);
        return ResultBuilder.succResult(rt);
    }

    @RequestMapping(value = "/login_user/v1/user/friends", method = RequestMethod.GET, consumes = APPLICATION_JSON_VALUE)
    @RestApi
    @ApiOperation(value = "查询某一具体用户的朋友信息", notes = "查询某一具体用户的朋友信息")
    public Result<List<FriendInfo>> getUserFriends() {
        List<FriendInfo> friendInfos = friendService.findByUserId(AuthenticationUserContextHolder.getCurrentUserId());
        return ResultBuilder.succResult(friendInfos);
    }
}
