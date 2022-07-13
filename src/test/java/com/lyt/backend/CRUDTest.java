package com.lyt.backend;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lyt.backend.models.Response;
import com.lyt.backend.models.User;
import org.junit.jupiter.api.*;
import org.junit.platform.commons.annotation.Testable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@Testable
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = MainTest.class)
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
public class CRUDTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper om = new ObjectMapper();

    @Test
    @Order(value = 0)
    public void testInsert() throws Exception {
        User entityToInsert = new User();
        entityToInsert.setName("张三");
        entityToInsert.setDescription("今天天气不行");
        entityToInsert.setDob(Date.from(Instant.parse("1993-02-09T00:00:00+08:00")));
        mockMvc.perform(
                post( "/v1/users").contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsString(entityToInsert))
        ).andDo(print()).andExpect(status().isCreated()).andExpectAll(
                jsonPath("$.data.id").value(1)
        );
        entityToInsert = new User();
        entityToInsert.setName("李四");
        entityToInsert.setDob(Date.from(Instant.parse("1992-04-05T00:00:00+08:00")));
        entityToInsert.setAddress("北京市 昌平区 XXX XXX");
        entityToInsert.setDescription("123456789");
        mockMvc.perform(
                post( "/v1/users").contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsString(entityToInsert))
        ).andDo(print()).andExpect(status().isCreated()).andExpectAll(
                jsonPath("$.data.id").value(2)
                );

    }

    @Test
    @Order(value = 1)
    public void testRetrieveAfterInsert() throws Exception {
        Response response = om.readValue(mockMvc.perform(
                get("/v1/users/2")
        ).andDo(print()).andExpect(status().isOk()).andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8), Response.class);
        Assertions.assertEquals(response.getData().getName(), "李四");
        Assertions.assertEquals(response.getData().getDob(), Date.from(Instant.parse("1992-04-05T00:00:00.000+08:00")));
        Assertions.assertEquals(response.getData().getId(), 2);
        Assertions.assertEquals(response.getData().getAddress(), "北京市 昌平区 XXX XXX");
        Assertions.assertEquals(response.getData().getDescription(), "123456789");
        mockMvc.perform(
                get("/v1/users/3")
        ).andExpect(status().isNotFound());
    }

    @Test
    @Order(value = 2)
    public void testUpdate() throws Exception {
        User entityToUpdate = new User();
        entityToUpdate.setId(1);
        entityToUpdate.setName("王五");
        entityToUpdate.setAddress("河南省 郑州市 郑东新区 XXXX");
        //entityToUpdate.setDescription("今天天气不错");
        mockMvc.perform(
                post("/v1/users/update").contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsString(entityToUpdate))
        ).andDo(print()).andExpect(status().isCreated()).andExpect(
                jsonPath("$.data.id").value(1)
        );
        Response response = om.readValue(mockMvc.perform(
                get("/v1/users/1")
        ).andDo(print()).andExpect(status().isOk()).andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8), Response.class);
        Assertions.assertEquals(response.getData().getName(), "王五");
        Assertions.assertEquals(response.getData().getDob(), Date.from(Instant.parse("1993-02-09T00:00:00.000+08:00")));
        Assertions.assertEquals(response.getData().getId(), 1);
        Assertions.assertEquals(response.getData().getAddress(), "河南省 郑州市 郑东新区 XXXX");
        Assertions.assertEquals(response.getData().getDescription(), "今天天气不行");
        entityToUpdate.setId(10);
        mockMvc.perform(
                post("/v1/users/update").contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsString(entityToUpdate))
        ).andExpect(status().isNotFound());
    }

    @Test
    @Order(value = 3)
    public void testDelete() throws Exception {
        mockMvc.perform(
                delete("/v1/users/1")
        ).andExpect(status().isNoContent());
        mockMvc.perform(
                delete("/v1/users/3")
        ).andExpect(status().isNotFound());
        mockMvc.perform(
                get("/v1/users/1")
        ).andExpect(status().isNotFound());
    }
}
