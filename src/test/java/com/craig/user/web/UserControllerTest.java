package com.craig.user.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.craig.user.TestApplication;
import com.craig.user.service.UserService;

@SpringBootTest(classes = TestApplication.class)
public class UserControllerTest {

    @MockBean
    private UserService userService;

    @Autowired
    private UserController userController;

    @Test
    void testCreateUser() {

    }

    @Test
    void testDeleteUser() {

    }

    @Test
    void testGetNearbyUser() {

    }

    @Test
    void testGetUserDetail() {

    }

    @Test
    void testGetUsers() {

    }

    @Test
    void testUpdateUser() {

    }
}
