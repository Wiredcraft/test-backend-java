package com.wiredcraft.user.tiny.modules.ums.controller;


import com.wiredcraft.user.tiny.common.api.CommonResult;
import com.wiredcraft.user.tiny.modules.ums.dto.FollowParam;
import com.wiredcraft.user.tiny.modules.ums.service.FollowerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

/**
 *
 * @author yuao
 * @since 2023-01-14
 */
@RestController
@RequestMapping("/user/follower")
@Api(tags = "FollowerController")
@Tag(name = "FollowerController",description = "follow")
public class FollowerController {


    @Autowired
    private FollowerService followerService;

    @ApiOperation(value = "follow")
    @RequestMapping(value = "/follow", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult<Boolean> follow(@Validated @RequestBody FollowParam followParam, Principal principal) {
        boolean result = followerService.follow(followParam.getFollowerName(), principal.getName());
        if (!result){
            return CommonResult.failed();
        }
        return CommonResult.success(result);
    }


    @ApiOperation(value = "User following list")
    @RequestMapping(value = "/followList", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult<List<String> > followList( Principal principal) {
        List<String> userFollow = followerService.getUserFollow(principal.getName());

        return CommonResult.success(userFollow);
    }



}

