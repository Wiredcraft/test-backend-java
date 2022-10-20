package com.wiredcraft.user.controller;

import com.wiredcraft.common.CommonResponse;
import com.wiredcraft.user.model.UserInfoDTO;
import com.wiredcraft.user.model.UserInfoVO;
import com.wiredcraft.user.service.UserInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jeremy.zhang
 * @date 2022-10-19
 */
@Api(value = "user service api")
@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserInfoController {
    private final UserInfoService userInfoService;

    @ApiOperation("create a new user")
    @PostMapping("/add")
    public CommonResponse<Void> createUser(@RequestBody UserInfoDTO userInfoDTO) {
        userInfoService.createUser(userInfoDTO);
        return CommonResponse.success(null);
    }

    @ApiOperation("modify user info")
    @PutMapping("/modify")
    public CommonResponse<Void> updateUser(@RequestBody UserInfoDTO userInfoDTO) {
        userInfoService.updateUser(userInfoDTO);
        return CommonResponse.success(null);
    }

    @ApiOperation("delete a user")
    @DeleteMapping("/{uuid}")
    public CommonResponse<Void> deleteUser(@PathVariable String uuid) {
        userInfoService.deleteUser(uuid);
        return CommonResponse.success(null);
    }

    @ApiOperation("query userInfo by userUuid")
    @GetMapping("/{uuid}")
    public CommonResponse<UserInfoVO> queryByUuid(@PathVariable String uuid) {
        return CommonResponse.success(userInfoService.queryByUuid(uuid));
    }
}
