package com.zhangyongxin.demo.controller;

import com.zhangyongxin.demo.common.Result;
import com.zhangyongxin.demo.common.ResultGenerator;
import com.zhangyongxin.demo.controller.base.BaseController;
import com.zhangyongxin.demo.model.user.UserInfo;
import com.zhangyongxin.demo.model.user.UserInsertInfo;
import com.zhangyongxin.demo.model.user.UserUpdateInfo;
import com.zhangyongxin.demo.service.user.UserInfoService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;

/**
 * user info
 *
 * @author zhangyongxin
 * @date 2022/3/19 19:52
 */
@RequestMapping("user")
@RestController
public class UserController extends BaseController {

    @Resource
    private UserInfoService userInfoService;

    @GetMapping("get")
    @ApiOperation(value = "获取登录用户的信息", notes = "获取登录用户的信息")
    public Result<UserInfo> get() {
        return ResultGenerator.genSuccessResult(getLoginUser());
    }

    @PostMapping("delete")
    @ApiOperation(value = "删除用户", notes = "注销并退出登录")
    public Result<Boolean> delete() {
        UserInfo userInfo = this.getLoginUser();
        boolean success = userInfoService.deleteOne(userInfo.getId());
        // 删除成功后登出用户
        if (success) {
            this.logout();
        }
        return ResultGenerator.genSuccessResult(success);
    }

    @PostMapping("update")
    @ApiOperation(value = "删除用户", notes = "注销并退出登录")
    public Result<Boolean> update(@RequestBody UserUpdateInfo updateInfo) {
        UserInfo userInfo = this.getLoginUser();
        userInfo.setUsername(updateInfo.getUsername());
        userInfo.setPassword(updateInfo.getPassword());
        userInfo.setDob(updateInfo.getDob());
        userInfo.setName(updateInfo.getName());
        userInfo.setDescription(updateInfo.getDescription());
        userInfo.setAddress(updateInfo.getAddress());
        boolean success = userInfoService.updateOne(userInfo);
        return ResultGenerator.genSuccessResult(success);
    }

    @PostMapping("insert")
    @ApiOperation(value = "创建用户", notes = "username 唯一不能重复,不需要登录")
    public Result<UserInfo> insert(@RequestBody UserInsertInfo userInsertInfo) {
        UserInfo userInfo = new UserInfo();
        userInfo.setUsername(userInsertInfo.getUsername());
        userInfo.setPassword(userInsertInfo.getPassword());
        userInfo.setDob(userInsertInfo.getDob());
        userInfo.setName(userInsertInfo.getName());
        userInfo.setDescription(userInsertInfo.getDescription());
        userInfo.setCreatedAt(new Date());
        userInfoService.addOne(userInfo);
        return ResultGenerator.genSuccessResult(userInfo);
    }


}
