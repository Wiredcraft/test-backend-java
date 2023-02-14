package com.w.t.module.service;

import com.w.t.module.service.impl.FollowService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class FollowServiceTest {

    @Autowired
    private FollowService followService;

    @Test
    void subscribe() {
        followService.subscribe(1L,7L);
    }

    @Test
    void unsubscribe() {
        followService.unsubscribe(1L,5L);
    }

    @Test
    void nearbyUsers() {
        followService.nearbyUsers("zhangxiang1");
    }

}
