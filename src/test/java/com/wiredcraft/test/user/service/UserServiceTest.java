package com.wiredcraft.test.user.service;

import com.wiredcraft.test.user.model.po.UserPO;
import com.wiredcraft.test.user.model.req.UserReq;
import com.wiredcraft.test.user.model.vo.UserVO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest

class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    void get() {
        UserReq userReq = buildUser();
        int id = userService.create(userReq);
        UserVO userVO = userService.get(id);
        Assertions.assertNotNull(userVO);
        Assertions.assertEquals(userVO.getName(), "raistlin");
    }

    @Test
    void create() {
        UserReq userReq = buildUser();
        int id = userService.create(userReq);
        Assertions.assertNotNull(id);
    }

    @Test
    void update() {
        UserReq userReq = buildUser();
        int id = userService.update(userReq);
        UserVO userVO = userService.get(id);
        Assertions.assertNotNull(userVO);
        Assertions.assertEquals(userVO.getName(), "raistlin");
    }

    @Test
    void delete() {
        UserReq userReq = buildUser();
        int id = userService.create(userReq);
        UserVO userVO = userService.get(id);
        userService.delete(id);
    }


    /**
     * Mock User
     *
     * @return
     */
    private static UserReq buildUser(){
        UserReq user = new UserReq();
        user.setName("raistlin");
        user.setAddress("address");
        user.setCreatedAt(new Date());
        return user;
    }

}