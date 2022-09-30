package com.coffee.user;

import com.coffee.user.request.UserUpdateParam;
import com.coffee.user.response.info.UserInfo;
import com.coffee.user.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

/**
 * user增删改查测试类
 * 增在用户注册时测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserCrudTest {

    private static final Logger logger = LoggerFactory.getLogger(UserCrudTest.class);

    @Autowired
    private UserService userService;

    @Test
    public void find() {
        UserInfo userInfo = userService.findOne("1");
        logger.info("userInfo = {}",userInfo.toString());
    }

    @Test
    public void delete() {
        userService.delete("2");
    }

    @Test
    public void update() {
        UserUpdateParam updateParam =new UserUpdateParam();
        updateParam.setAddress("上海市黄浦区");
        updateParam.setDescription("我超长长我超长长我超长长我超长长我超长长我超长长我超长长我超长长我超长长我超长长我超长长我超长长我超长长我超长长");
        updateParam.setDob(new Date());
        updateParam.setId("3");
        userService.update(updateParam);
    }
}
