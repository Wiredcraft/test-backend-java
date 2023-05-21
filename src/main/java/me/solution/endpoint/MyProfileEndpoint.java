package me.solution.endpoint;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import me.solution.model.domain.User;
import me.solution.model.reqresp.UpdatePasswdReq;
import me.solution.model.reqresp.UpdateProfileReq;
import me.solution.service.biz.ProfileService;
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
    private ProfileService profileService;

    @ApiOperation("get my profile")
    @GetMapping("/myProfile")
    public User myProfile() {
        Long userId = LoginUtils.getUserIdRequireNonNull();
        User user = profileService.getProfileById(userId);
        return user;
    }

    @ApiOperation("update my profile")
    @PostMapping("/updateProfile")
    public void updateProfile(@RequestBody UpdateProfileReq req) {
        Long userId = LoginUtils.getUserIdRequireNonNull();
        profileService.updateProfile(userId, req);
    }

    @ApiOperation("update my passwd")
    @PostMapping("/updatePasswd")
    public void updatePasswd(@RequestBody UpdatePasswdReq req) {
        Long userId = LoginUtils.getUserIdRequireNonNull();
        profileService.updatePasswd(userId, req.getPasswd());
    }

    @ApiOperation("delete my account")
    @PostMapping("/delAccount")
    public void delAccount() {
        Long userId = LoginUtils.getUserIdRequireNonNull();
        profileService.delAccount(userId);
    }
}
