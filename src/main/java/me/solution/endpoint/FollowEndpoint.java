package me.solution.endpoint;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import me.solution.model.reqresp.FollowResp;
import me.solution.model.reqresp.ResultResp;
import me.solution.security.utils.LoginUtil;
import me.solution.service.biz.FollowBizService;
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
    public ResultResp<Void> addFollowing(@RequestParam("userId") Long userId) {
        Long myUserId = LoginUtil.getUserIdRequireNonNull();
        followBizService.addFollowing(myUserId, userId);

        return ResultResp.success();
    }

    @ApiOperation("delFollowing")
    @PostMapping("/delFollowing")
    public ResultResp<Void> delFollowing(@RequestParam("userId") Long userId) {
        Long myUserId = LoginUtil.getUserIdRequireNonNull();
        followBizService.unFollowing(myUserId, userId);

        return ResultResp.success();
    }

    @ApiOperation("list my following")
    @GetMapping("/listMyFollowings")
    public ResultResp<List<FollowResp>> listMyFollowings() {
        Long myUserId = LoginUtil.getUserIdRequireNonNull();
        List<FollowResp> result = followBizService.listFollowings(myUserId);
        return ResultResp.successData(result);
    }

    @ApiOperation("list my followers")
    @GetMapping("/listMyFollowers")
    public ResultResp<List<FollowResp>> listMyFollowers() {
        // TODO: 2023/5/24
        return ResultResp.successData(Collections.emptyList());
    }
}
