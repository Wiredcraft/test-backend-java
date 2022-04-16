package com.test.backend.java;

import com.alibaba.fastjson.JSONObject;
import com.test.backend.java.controller.UserController;
import com.test.backend.java.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class UserControllerTests {

    @Autowired
    private UserController userController;

    @Test
    public void testGet() {
        User user = userController.get("U001");
        System.out.println("get user: " + JSONObject.toJSON(user));
    }

    @Test
    public void testCreate() {
        User user = new User();
        user.setUserId("U004");
        user.setName("zhu1");
        user.setDob("1995-02-01");
        boolean result = userController.create(user);
        System.out.println("create user: " + result);
    }

    @Test
    public void testUpdate() {
        User user = new User();
        user.setUserId("U004");
        user.setDob("1995-02-01");
        boolean result = userController.update(user);
        System.out.println("update user: " + result);
    }

    @Test
    public void testDelete() {
        User user = new User();
        user.setUserId("U004");
        boolean result = userController.delete(user);
        System.out.println("delete user: " + result);
    }

    @Test
    public void testGetFriends() {
        List<User> friends = userController.getFriends("U001");
        System.out.println("get user friends: " + JSONObject.toJSON(friends));
    }
}
