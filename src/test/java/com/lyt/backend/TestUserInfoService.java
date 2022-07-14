package com.lyt.backend;

import com.lyt.backend.service.LoginInfoService;
import com.lyt.backend.service.UserInfoService;
import org.junit.jupiter.api.*;
import org.junit.platform.commons.annotation.Testable;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.sql.Date;
import java.time.Instant;

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
        Assertions.assertDoesNotThrow(() -> userInfoService.updateAddressAndDescriptionAndDob("拜登", "白宫",null,
                Date.from(Instant.parse("2020-07-01T10:00:00+08:00"))));
        Assertions.assertDoesNotThrow(() -> userInfoService.updateAddressAndDescriptionAndDob("普京", null, "今天速通乌克兰", Date.from(Instant.parse("2022-02-24T10:00:00+08:00"))));
        Assertions.assertEquals(userInfoService.getUserInfo("拜登").getAddress(), "白宫");
        Assertions.assertEquals(userInfoService.getUserInfo("拜登").getDob().toInstant(),Instant.parse("2020-07-01T02:00:00Z"));
        Assertions.assertEquals(userInfoService.getUserInfo("普京").getDescription(), "今天速通乌克兰");
        Assertions.assertEquals(userInfoService.getUserInfo("普京").getDob().toInstant(), Instant.parse("2022-02-24T02:00:00Z"));


    }
}
