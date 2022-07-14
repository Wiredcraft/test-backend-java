package com.lyt.backend;

import com.lyt.backend.service.LoginInfoService;
import com.lyt.backend.service.UserInfoService;
import org.junit.jupiter.api.*;
import org.junit.platform.commons.annotation.Testable;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@Testable
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = MainTest.class)
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
public class TestUserInfoService {
    @Resource
    private UserInfoService userInfoService;

    @Resource
    private LoginInfoService loginInfoService;

    @Test
    @Order(value = 0)
    public void testUpdateAndGetUserInfo1() {
        Assertions.assertTrue(loginInfoService.register("拜登", "abcdefg"));
        Assertions.assertTrue(loginInfoService.register("普京", "abcdefg"));
        Assertions.assertDoesNotThrow(() -> userInfoService.updateAddressAndDescription("拜登", "白宫",null));
        Assertions.assertDoesNotThrow(() -> userInfoService.updateAddressAndDescription("普京", null, "今天速通乌克兰"));
        Assertions.assertEquals(userInfoService.getUserInfo("拜登").getAddress(), "白宫");
        Assertions.assertEquals(userInfoService.getUserInfo("普京").getDescription(), "今天速通乌克兰");

    }
}
