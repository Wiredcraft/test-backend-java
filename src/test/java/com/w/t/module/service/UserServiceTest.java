package com.w.t.module.service;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import com.w.t.module.entity.User;
import com.w.t.module.entity.dto.UserDTO;
import com.w.t.module.service.impl.UserService;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    void registerUser() {
        userService.registerUser("zhangxiang1", "123");
        User user = userService.getUserByName("zhangxiang1");
        Assert.assertNotNull("the user register failed", user);
        userService.removeUserById(user.getId());
    }

    @Test
    void login() {
        String token = userService.login("zhangxiang", "123");
        Assert.assertNotNull("the user login failed",token);
    }

    @Test
    void createUser() {
        UserDTO userDTO=new UserDTO();
        userDTO.setName("zhangxiang2");
        userDTO.setAddress("address");
        userDTO.setDescription("test user");
        userDTO.setDob("20221212");
        userDTO.setLatitude(12.123);
        userDTO.setLongitude(112.21);
        User user=new User();
        BeanUtils.copyProperties(userDTO,user);
        userService.createUser(user);
        User user1 = userService.getUserByName("zhangxiang1");
        Assert.assertNotNull("create a user failed", user1);
    }

    @Test
    void getUserById() {
        User user1 = userService.getUserById(1L);
        Assert.assertNotNull("the user does not existed.", user1);
    }

}
