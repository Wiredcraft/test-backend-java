package com.craig.user.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.craig.user.TestApplication;
import com.craig.user.service.FollowerService;

@SpringBootTest(classes = TestApplication.class)
public class FollowControllerTest {
    @MockBean
    private FollowerService followerService;

    @Autowired
    private FollowController followController;

    @Test
    void testAddFollowers() {

    }

    @Test
    void testGetFollowers() {

    }

    @Test
    void testGetFollowings() {

    }

    @Test
    void testRemoveFollowers() {

    }
}
