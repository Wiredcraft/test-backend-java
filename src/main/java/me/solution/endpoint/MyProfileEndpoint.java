package me.solution.endpoint;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import me.solution.model.domain.User;
import me.solution.model.reqresp.ResultResp;
import me.solution.model.reqresp.UpdatePasswdReq;
import me.solution.model.reqresp.UpdateProfileReq;
import me.solution.service.biz.ProfileBizService;
import me.solution.utils.LoginUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * endpoint for my profile
 *
 * @author davincix
 * @since 2023/5/22 02:16
 */
@Api(tags = "my profile endpoint")
@RestController
@RequestMapping("/user")
public class MyProfileEndpoint {
    @Autowired
    private ProfileBizService profileBizService;

    @ApiOperation("get my profile")
    @GetMapping("/myProfile")
    public ResultResp<User> myProfile() {
        Long userId = LoginUtils.getUserIdRequireNonNull();
        User user = profileBizService.getProfileById(userId);
        return ResultResp.successData(user);
    }

    @ApiOperation("update my profile")
    @PostMapping("/updateProfile")
    public ResultResp<Void> updateProfile(@RequestBody UpdateProfileReq req) {
        Long userId = LoginUtils.getUserIdRequireNonNull();
        profileBizService.updateProfile(userId, req);

        return ResultResp.success();
    }

    @ApiOperation("update my passwd")
    @PostMapping("/updatePasswd")
    public ResultResp<Void> updatePasswd(@RequestBody UpdatePasswdReq req) {
        Long userId = LoginUtils.getUserIdRequireNonNull();
        profileBizService.updatePasswd(userId, req.getPasswd());

        return ResultResp.success();
    }

    @ApiOperation("delete my account")
    @PostMapping("/delAccount")
    public ResultResp<Void> delAccount() {
        Long userId = LoginUtils.getUserIdRequireNonNull();
        profileBizService.delAccount(userId);

        return ResultResp.success();
    }
}
