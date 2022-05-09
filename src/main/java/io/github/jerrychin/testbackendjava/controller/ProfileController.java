package io.github.jerrychin.testbackendjava.controller;


import io.github.jerrychin.testbackendjava.dto.UserDTO;
import io.github.jerrychin.testbackendjava.dto.UserVO;
import io.github.jerrychin.testbackendjava.entity.Account;
import io.github.jerrychin.testbackendjava.service.ProfileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@Api(tags="User Profile")
@Slf4j
@RestController
@RequestMapping("/api/v1/profile")
public class ProfileController {

    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @ApiOperation("Get Profile")
    @GetMapping("")
    public UserVO getProfile(@ApiIgnore @AuthenticationPrincipal Account account) {
        return profileService.getProfile(account.getId());
    }

    @ApiOperation("Update Profile")
    @PutMapping("")
    public UserVO updateProfile(@ApiIgnore @AuthenticationPrincipal Account account, @RequestBody UserDTO userDTO) {
       return profileService.updateProfile(account.getId(), userDTO);
    }
}