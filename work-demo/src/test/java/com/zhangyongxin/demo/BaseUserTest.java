package com.zhangyongxin.demo;

import com.alibaba.fastjson.JSON;
import com.zhangyongxin.demo.model.user.LoginUserParam;
import com.zhangyongxin.demo.model.user.UserInfo;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Date;
import java.util.Random;

/**
 * 用户测试专用
 *
 * @author zhangyongxin
 * @date 2022/3/20 13:02
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BaseUserTest {

    @Autowired
    private WebApplicationContext context;

    protected MockMvc mockMvc;

    protected MockHttpSession session;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        session = new MockHttpSession();
        UserInfo userInfo = createUser();
        login(userInfo.getUsername(), userInfo.getPassword());
    }

    private void login(String username, String password) throws Exception {
        // 登陆
        MockHttpServletRequestBuilder loginRequestBuilder = MockMvcRequestBuilders.post("/login")
                .content(JSON.toJSONString(new LoginUserParam(username, password)))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON)
                .session(session);
        mockMvc.perform(loginRequestBuilder);
    }

    /**
     * 创建用户
     *
     * @return
     * @throws Exception
     */
    protected UserInfo createUser() throws Exception {
        String url = "/user/insert";
        UserInfo userInfo = new UserInfo();
        Random random = new Random();
        int randomInt = random.nextInt(10000);
        userInfo.setUsername("U:" + randomInt);
        userInfo.setPassword("test");
        userInfo.setDob(new Date());
        userInfo.setName("jake " + randomInt);
        userInfo.setDescription("随机测试账户");
        RequestBuilder request = MockMvcRequestBuilders.post(url).session(session)
                .content(JSON.toJSONString(userInfo))
                .contentType(MediaType.APPLICATION_JSON_UTF8);
        this.mockMvc.perform(request);
        return userInfo;
    }

}
