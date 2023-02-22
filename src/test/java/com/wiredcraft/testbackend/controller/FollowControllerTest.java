package com.wiredcraft.testbackend.controller;

import com.wiredcraft.testbackend.entity.PageResult;
import com.wiredcraft.testbackend.entity.Result;
import com.wiredcraft.testbackend.entity.ResultsCode;
import com.wiredcraft.testbackend.entity.User;
import com.wiredcraft.testbackend.entity.param.PageParam;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.Collectors;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class FollowControllerTest {

    @Autowired
    private FollowController followController;

    private static Long originalUserId = 1L;
    private static Long targetUserId = 2L;

    @Test
    @Order(1)
    void follow() {
        followController.unfollow(originalUserId, targetUserId);
        Result<Boolean> result = followController.follow(originalUserId, targetUserId);
        Assertions.assertEquals(ResultsCode.SUCCESS.getCode(), result.getCode());
    }

    @Test
    @Order(2)
    void getFanById() {
        Result<PageResult<User>> result = followController.getFanById(targetUserId, new PageParam());
        Assertions.assertEquals(true, result.getData() != null && result.getData().getTotal() > 0);

        Assertions.assertEquals(true, result.getData() != null
                && result.getData().getData().stream().map(e -> e.getId())
                .collect(Collectors.toSet()).contains(originalUserId));
    }

    @Test
    @Order(3)
    void getFollowById() {
        Result<PageResult<User>> result = followController.getFollowById(originalUserId, new PageParam());
        Assertions.assertEquals(true, result.getData() != null && result.getData().getTotal() > 0);

        Assertions.assertEquals(true, result.getData() != null
                && result.getData().getData().stream().map(e -> e.getId())
                .collect(Collectors.toSet()).contains(targetUserId));
    }

    @Test
    @Order(4)
    void unfollow() {
        Result<Boolean> result = followController.unfollow(originalUserId, targetUserId);
        Assertions.assertEquals(ResultsCode.SUCCESS.getCode(), result.getCode());
    }

    @Test
    @Order(5)
    void getFollowById2() {
        Result<PageResult<User>> result = followController.getFollowById(originalUserId, new PageParam());
        Assertions.assertEquals(true, result.getData() != null
                && (result.getData().getData() == null || !result.getData().getData().stream()
                .map(e -> e.getId()).collect(Collectors.toSet()).contains(targetUserId)));
    }
}