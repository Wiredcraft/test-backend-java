package com.lyt.backend;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lyt.backend.models.Response;
import com.lyt.backend.service.LoginInfoService;
import org.junit.jupiter.api.*;
import org.junit.platform.commons.annotation.Testable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Testable
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = MainTest.class)
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
public class TestUserInfoController {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private LoginInfoService loginInfoService;

    private final ObjectMapper om = new ObjectMapper();

    @Test
    @Order(value = 0)
    public void testInsertInfo() {
        loginInfoService.register("张三", "12345");
        loginInfoService.register("李四", "abcde");

    }

    @Test
    @Order(value = 1)
    @WithUserDetails("张三")
    public void testUpdateMyInfo1() throws Exception {
        mockMvc.perform(post("/v1/user?address=北京市昌平区XXX&description=今天下大雨")).andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    @Order(value = 2)
    @WithUserDetails("李四")
    public void testUpdateMyInfo2() throws Exception {
        mockMvc.perform(post("/v1/user?address=北京市朝阳区XXX")).andDo(print())
                .andExpect(status().isCreated());
    }


    @Test
    @Order(value = 3)
    public void testGetInfo() throws Exception {
        Response response = om.readValue(mockMvc.perform(get("/v1/user/张三")).andExpect(status().isOk())
                .andDo(print()).andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8), Response.class);
        Assertions.assertEquals(response.getData().getAddress(), "北京市昌平区XXX");
        Assertions.assertEquals(response.getData().getDescription(), "今天下大雨");
        Response response1 = om.readValue(mockMvc.perform(get("/v1/user/李四")).andExpect(status().isOk())
                .andDo(print()).andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8), Response.class);
        Assertions.assertEquals(response1.getData().getAddress(), "北京市朝阳区XXX");
    }
}
