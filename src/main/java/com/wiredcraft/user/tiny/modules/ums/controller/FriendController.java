package com.wiredcraft.user.tiny.modules.ums.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wiredcraft.user.tiny.common.api.CommonPage;
import com.wiredcraft.user.tiny.common.api.CommonResult;
import com.wiredcraft.user.tiny.modules.ums.dto.UserLocationParam;
import com.wiredcraft.user.tiny.modules.ums.model.Friend;

import com.wiredcraft.user.tiny.modules.ums.service.FriendService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

/**
 *
 * @author yuao
 * @since 2023-01-14
 */
@RestController
@RequestMapping("/user/friend")
@Api(tags = "FriendController")
@Tag(name = "FriendController",description = "friend controller")
public class FriendController {


    @Autowired
    private FriendService friendService;

    @ApiOperation("get friend list")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<CommonPage<Friend>> list(Principal principal,
                                                 @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                                                 @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        Page<Friend> adminList =friendService .list(principal.getName(), pageSize, pageNum);
        return CommonResult.success(CommonPage.restPage(adminList));
    }


    @ApiOperation("get  nearby friends ")
    @RequestMapping(value = "/nearBy", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult<List<String>> nearBy(Principal principal,  @RequestBody UserLocationParam param) {
        List<String> friendList =friendService .nearByFriends(principal.getName(),param.getLatitude(),param.getLongitude());
        return CommonResult.success(friendList);
    }
}

