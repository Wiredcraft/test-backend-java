package com.coffee.user;

import com.coffee.user.request.AuthLoginParam;
import com.coffee.user.request.UserRegisterParam;
import com.coffee.user.response.info.AuthLoginInfo;
import com.coffee.user.service.AuthenticationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserLoginTest {
    private static final Logger logger = LoggerFactory.getLogger(UserLoginTest.class);

    @Autowired
    private AuthenticationService authenticationService;

    @Test
    public void redister() {
        UserRegisterParam userRegisterParam =new UserRegisterParam();
        userRegisterParam.setAddress("上海市虹口区");
        userRegisterParam.setCellphone("13322224444");
        userRegisterParam.setDescription("描述");
//        userRegisterParam.setDob(new);
        userRegisterParam.setPassword("tvghjbhjkkkkk");
        userRegisterParam.setName("Mia");
        userRegisterParam.setVerifyCode("1");
        authenticationService.register(userRegisterParam);
    }

    @Test
    public void login() {
        AuthLoginParam loginParam =new AuthLoginParam();
        loginParam.setName("Mia");
        loginParam.setPassword("tvghjbhjkkkkk");
        AuthLoginInfo authLoginInfo = authenticationService.login(loginParam);
        logger.info("authLoginInfo={}", authLoginInfo.toString());
    }

}
