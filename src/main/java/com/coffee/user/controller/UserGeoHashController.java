package com.coffee.user.controller;

import com.coffee.user.annotation.RestApi;
import com.coffee.user.context.AuthenticationUserContextHolder;
import com.coffee.user.request.UserGeohashCreateParam;
import com.coffee.user.response.Result;
import com.coffee.user.response.info.NearbyFriendsInfo;
import com.coffee.user.response.info.ResultBuilder;
import com.coffee.user.service.UserGeoHashService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


/**
 * UserFriend定位及附近的人实现
 */
@RestController
@Slf4j
@Api(value = "用户定位及附近的人API", description = "用户定位及附近的人API", tags = {"UserGeoHashController"})
public class UserGeoHashController {
    @Autowired
    private UserGeoHashService userGeoHashService;

    @RequestMapping(value = "/login_user/v1/user/user_geo_hash/create_or_update", method = RequestMethod.POST, consumes = APPLICATION_JSON_VALUE)
    @RestApi
    @ApiOperation(value = "更新或新建用户定位信息", notes = "更新或新建用户定位信息")
    public Result<Boolean> getUserInfo(@RequestBody UserGeohashCreateParam createParam) {
        if (StringUtils.isBlank(createParam.getUserId())) {
            createParam.setUserId(AuthenticationUserContextHolder.getCurrentUserId());
        }
        Boolean rt = userGeoHashService.createOrUpdateUserGeohash(createParam);
        return ResultBuilder.succResult(rt);
    }

    @RequestMapping(value = "/login_user/v1/user/near_by_friends", method = RequestMethod.POST, consumes = APPLICATION_JSON_VALUE)
    @RestApi
    @ApiOperation(value = "获取用户附近的朋友", notes = "获取用户附近的朋友")
    public Result<NearbyFriendsInfo> getUserInfo(@ApiParam(name = "搜索范围 km", required = true) @RequestParam("distance") double distance,
                                                 @ApiParam(name = "geoHash精度", defaultValue = "12") @RequestParam(required = false, defaultValue = "12", value = "len") int len) {
        NearbyFriendsInfo nearbyFriendsInfo = userGeoHashService.findNearbyFriends(
                AuthenticationUserContextHolder.getCurrentUserId(),
                distance,
                len
        );
        nearbyFriendsInfo.setUserName(AuthenticationUserContextHolder.getUserInfo().getName());
        return ResultBuilder.succResult(nearbyFriendsInfo);
    }
}
