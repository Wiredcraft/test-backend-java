package me.solution.endpoint;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import me.solution.model.domain.User;
import me.solution.service.biz.FollowBizService;
import me.solution.utils.LoginUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

/**
 * follower/following endpoint
 *
 * @author davincix
 * @since 2023/5/22 05:13
 */
@Api(tags = "follow endpoint")
@RestController
@RequestMapping("/follow")
public class FollowEndpoint {
    @Autowired
    private FollowBizService followBizService;

    @ApiOperation("addFollowing")
    @PostMapping("/addFollowing")
    public String addFollowing(@RequestParam("userId") Long userId) {
        Long myUserId = LoginUtils.getUserIdRequireNonNull();
        followBizService.addFollowing(myUserId, userId);

        return null;
    }

    @ApiOperation("unFollowing")
    @PostMapping("/unFollowing")
    public String unFollowing(@RequestParam("userId") Long userId) {
        Long myUserId = LoginUtils.getUserIdRequireNonNull();
        followBizService.unFollowing(myUserId, userId);

        return null;
    }

    @ApiOperation("list my following")
    @GetMapping("/listMyFollowings")
    public List<User> listMyFollowings() {
        // TODO: 2023/5/22
        return Collections.emptyList();
    }

    @ApiOperation("list my followers")
    @GetMapping("/listMyFollowers")
    public List<User> listMyFollowers() {
        // TODO: 2023/5/22
        return Collections.emptyList();
    }
}
