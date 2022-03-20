package com.zhangyongxin.demo;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.nio.charset.StandardCharsets;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * helloWorld测试
 * @author zhangyongxin
 * @date 2022/3/18 13:38
 */
@Slf4j
public class HelloWorldControllerTest extends BaseApplicationTest {

    @Test
    public void helloNotLogin() throws Exception {
        String url = "/test/hello";
        RequestBuilder request = MockMvcRequestBuilders.get(url)
                .contentType(MediaType.APPLICATION_JSON_UTF8);
        ResultActions resultActions = this.mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("403"))
                .andDo(print());
        this.printResponse(resultActions);
    }

    @Test
    public void helloUserLogin() throws Exception {
        String url = "/test/hello";
        RequestBuilder request = MockMvcRequestBuilders.get(url).session(this.session)
                .contentType(MediaType.APPLICATION_JSON).characterEncoding(StandardCharsets.UTF_8.toString());
        ResultActions resultActions = this.mockMvc.perform(request)
                .andExpect(status().isOk()).andDo(print());
        this.printResponse(resultActions);
    }
}
