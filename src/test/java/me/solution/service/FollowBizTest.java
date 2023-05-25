package me.solution.service;

import me.solution.service.biz.FollowBizService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * test for #{@link me.solution.service.biz.FollowBizService}
 *
 * @author davincix
 * @since 2023/5/20 19:08
 */
@SpringBootTest
public class FollowBizTest {
    @Autowired
    private FollowBizService followBizService;

    @Test
    public void testFollowing() {
//        followBizService.addFollowing(1L, 2L);
        followBizService.addFollowing(2L, 1L);
    }

    @Test
    public void testUnFollowing() {
        followBizService.unFollowing(1L, 2L);
//        followBizService.unFollowing(2L, 1L);
    }
}
