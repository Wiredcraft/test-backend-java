package me.solution.endpoint;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import me.solution.model.reqresp.ResultResp;
import me.solution.model.reqresp.UpdatePasswdReq;
import me.solution.model.reqresp.UpdateProfileReq;
import me.solution.model.reqresp.UserResp;
import me.solution.service.biz.ProfileBizService;
import me.solution.common.utils.LoginUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * endpoint for my profile
 *
 * @author davincix
 * @since 2023/5/22 02:16
 */
@Api(tags = "profile endpoint")
@RestController
@RequestMapping("/user")
public class ProfileEndpoint {
    @Autowired
    private ProfileBizService profileBizService;

    @ApiOperation("get my profile")
    @GetMapping("/myProfile")
    public ResultResp<UserResp> myProfile() {
        Long userId = LoginUtil.getUserIdRequireNonNull();
        UserResp result = profileBizService.getProfileById(userId);
        return ResultResp.successData(result);
    }

    @ApiOperation("update my profile")
    @PostMapping("/updateProfile")
    public ResultResp<Void> updateProfile(@RequestBody UpdateProfileReq req) {
        Long userId = LoginUtil.getUserIdRequireNonNull();
        profileBizService.updateProfile(userId, req);

        return ResultResp.success();
    }

    @ApiOperation("update my passwd")
    @PostMapping("/updatePasswd")
    public ResultResp<Void> updatePasswd(@RequestBody @Valid UpdatePasswdReq req) {
        Long userId = LoginUtil.getUserIdRequireNonNull();
        profileBizService.updatePasswd(userId, req.getPasswd());

        return ResultResp.success();
    }

    @ApiOperation("delete my account")
    @PostMapping("/delAccount")
    public ResultResp<Void> delAccount() {
        Long userId = LoginUtil.getUserIdRequireNonNull();
        profileBizService.delAccount(userId);

        return ResultResp.success();
    }
}
