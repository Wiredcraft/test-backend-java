package com.lyt.backend;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lyt.backend.service.LoginInfoService;
import org.junit.jupiter.api.*;
import org.junit.platform.commons.annotation.Testable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Testable
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = MainTest.class)
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
public class TestAuthControllerV1 {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private LoginInfoService loginInfoService;

    @Autowired
    private PasswordEncoder encoder;

    private final ObjectMapper om = new ObjectMapper();

    @Test
    @Order(value = 0)
    public void testCreateUser() throws Exception {
        mockMvc.perform(post("/v1/register?username=王五&password=123456")).andExpect(status().isCreated()).andDo(print());
        Assertions.assertTrue( encoder.matches("123456",loginInfoService.loadUserByUsername("王五").getPassword()));
    }

    @Test
    @Order(value = 1)
    @WithUserDetails("王五")
    public void testChangePasswordDeregister() throws Exception {
        mockMvc.perform(patch("/v1/user?newPassword=567890")).andExpect(status().isCreated()).andDo(print());
        Assertions.assertTrue(encoder.matches("567890", loginInfoService.loadUserByUsername("王五").getPassword()));
        mockMvc.perform(delete("/v1/user")).andExpect(status().isNoContent());
        Assertions.assertThrows(UsernameNotFoundException.class, () -> loginInfoService.loadUserByUsername("王五"));
    }
}
