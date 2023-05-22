package me.solution.endpoint;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import me.solution.model.domain.User;
import me.solution.model.reqresp.ResultResp;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
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

    @ApiOperation("list nearby friends")
    @PostMapping("/listNearby")
    public ResultResp<List<User>> listNearby(@RequestParam("name") String name) {
        // TODO: 2023/5/22 redis geo-hash

        return ResultResp.successData(Collections.emptyList());
    }
}
