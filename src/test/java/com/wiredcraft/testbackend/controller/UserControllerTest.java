package com.wiredcraft.testbackend.controller;

import com.wiredcraft.testbackend.entity.PageResult;
import com.wiredcraft.testbackend.entity.Result;
import com.wiredcraft.testbackend.entity.ResultsCode;
import com.wiredcraft.testbackend.entity.User;
import com.wiredcraft.testbackend.entity.param.PageParam;
import com.wiredcraft.testbackend.entity.param.UserParam;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserControllerTest {

    @Autowired
    private UserController userController;

    private static User user;

    @BeforeAll
    static void init() {
        user = new User();
        user.setName("zhangsan-" + System.currentTimeMillis());
        user.setPassword("password");
        user.setDob(Date.valueOf("2010-10-20"));
        user.setAddress("shanghai");
        user.setDescription("An outgoing and studious programmer");
        user.setCreatedAt(new java.util.Date());
    }

    @Test
    @Order(2)
    void getUserById() {
        // test with normal id
        Result<User> result = userController.getUserById(user.getId());
        Assertions.assertEquals(true, result.getData() != null);

        // test with non-existent id
        Result<User> result2 = userController.getUserById(-1L);
        Assertions.assertEquals(true, result2.getData() == null);
    }

    @Test
    @Order(3)
    void getUserList() {
        User param = new User();
        param.setName(user.getName());
        Result<PageResult<User>> result = userController.getUserList(param, new PageParam());
        Assertions.assertEquals(ResultsCode.SUCCESS.getCode(), result.getCode());
    }

    @Test
    @Order(1)
    void createUser() {
        UserParam param = new UserParam();
        BeanUtils.copyProperties(user, param);
        Result<User> result = userController.createUser(param);
        user = result.getData();
        Assertions.assertEquals(true, result.getCode() == ResultsCode.SUCCESS.getCode() && result.getData().getId() != null && result.getData().getPassword().startsWith("$2a$10$"));
    }

    @Test
    @Order(4)
    void updateUser() {
        // test with normal id
        UserParam param = new UserParam();
        BeanUtils.copyProperties(user, param);
        Result<User> result = userController.updateUser(user.getId(), param);
        Assertions.assertEquals(ResultsCode.SUCCESS.getCode(), result.getCode());
        Assertions.assertEquals(true, result.getData().getPassword().startsWith("$2a$10$"));

        // test with non-existent id
        Result<User> result2 = userController.updateUser(-1L, param);
        Assertions.assertEquals(ResultsCode.FAIL.getCode(), result2.getCode());
    }

    @Test
    @Order(5)
    void deleteUserById() {
        Result<Boolean> result = userController.deleteUserById(user.getId());
        Assertions.assertEquals(ResultsCode.SUCCESS.getCode(), result.getCode());

        // test with non-existent id
        Result<Boolean> result2 = userController.deleteUserById(-1L);
        Assertions.assertEquals(ResultsCode.FAIL.getCode(), result2.getCode());
    }

}