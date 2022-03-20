package com.zhangyongxin.demo;

import com.alibaba.fastjson.JSON;
import com.zhangyongxin.demo.model.user.LoginUserParam;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.StandardCharsets;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class BaseApplicationTest {

    @Autowired
    private WebApplicationContext context;

    protected MockMvc mockMvc;

    protected MockHttpSession session;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        session = new MockHttpSession();
        login();
    }

    private void login() throws Exception {
        // 登陆
        MockHttpServletRequestBuilder loginRequestBuilder = MockMvcRequestBuilders.post("/login")
                .content(JSON.toJSONString(new LoginUserParam("admin", "admin")))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON)
                .session(session);
        mockMvc.perform(loginRequestBuilder);
    }

    protected void printResponse(ResultActions resultActions) throws Exception {
        MockHttpServletResponse response = resultActions.andReturn().getResponse();
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
        log.info(response.getContentAsString());
    }

}
