package com.zhangyongxin.demo;

import com.alibaba.fastjson.JSON;
import com.zhangyongxin.demo.model.user.UserInfo;
import com.zhangyongxin.demo.model.user.UserUpdateInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Date;
import java.util.Random;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author zhangyongxin
 * @date 2022/3/19 20:06
 */
@Slf4j
public class UserControllerTest extends BaseUserTest {

    /**
     * 创建随机账户后登录查询
     * @throws Exception
     */
    @Test
    public void getUser() throws Exception {
        String url = "/user/get";
        RequestBuilder request = MockMvcRequestBuilders.get(url).session(this.session)
                .contentType(MediaType.APPLICATION_JSON_UTF8);
        this.mockMvc.perform(request)
                .andExpect(status().isOk())
                .andDo(print());
    }

    /**
     * 创建随机账户
     * @throws Exception
     */
    @Test
    public void insertUser() throws Exception {
        UserInfo userInfo = this.createUser();
        log.info(JSON.toJSONString(userInfo));
        assert userInfo != null;
    }

    /**
     * 创建随机账户，登录后删除
     * @throws Exception
     */
    @Test
    public void deleteUser() throws Exception {
        String url = "/user/delete";
        RequestBuilder request = MockMvcRequestBuilders.post(url).session(this.session)
                .contentType(MediaType.APPLICATION_JSON_UTF8);
        this.mockMvc.perform(request)
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void updateUser() throws Exception {
        String url = "/user/update";
        UserUpdateInfo updateInfo = new UserUpdateInfo();
        updateInfo.setAddress("Nanjing Jiangsu China");
        RequestBuilder request = MockMvcRequestBuilders.post(url).session(this.session)
                .content(JSON.toJSONString(updateInfo))
                .contentType(MediaType.APPLICATION_JSON_UTF8);
        this.mockMvc.perform(request)
                .andExpect(status().isOk())
                .andDo(print());
        String getUrl = "/user/get";
        RequestBuilder getRequest = MockMvcRequestBuilders.get(getUrl).session(this.session)
                .contentType(MediaType.APPLICATION_JSON_UTF8);
        this.mockMvc.perform(getRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.address").value(updateInfo.getAddress()))
                .andDo(print());
    }
}
