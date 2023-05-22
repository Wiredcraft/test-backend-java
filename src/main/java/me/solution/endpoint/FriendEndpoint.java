package me.solution.endpoint;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import me.solution.model.domain.User;
import me.solution.service.biz.FollowBizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * endpoint for following -> <- follower friends
 *
 * @author davincix
 * @since 2023/5/22 10:42
 */
@Api(tags = "friend endpoint")
@RestController
@RequestMapping("/friend")
public class FriendEndpoint {
    @Autowired
    private FollowBizService followBizService;

    @ApiOperation("list nearby friends")
    @PostMapping("/listNearby")
    public List<User> listNearby(@RequestParam("name") String name) {
        List<User> friends = followBizService.listNearbyFriends(name);
        return friends;
    }
}
