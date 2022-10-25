package com.craig.user.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.craig.user.TestApplication;
import com.craig.user.entity.User;
import com.craig.user.model.LoginModel;
import com.craig.user.model.LoginResultModel;
import com.craig.user.repository.UserRepository;
import com.craig.user.util.JwtTokenUtil;

@SpringBootTest(classes = TestApplication.class)
public class AuthServiceImplTest {
    @MockBean
    private UserRepository userRepository;

    @Autowired
    private AuthServiceImpl authService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @BeforeEach
    public void setUp() {
        Mockito.reset(userRepository);

        //use static password "admin" for each user
        Mockito.when(userRepository.getByName(Mockito.anyString())).thenAnswer(a->{
            String userName = a.getArgument(0, String.class);
            User mockAdmin = new User();
            mockAdmin.setId(-1L);
            mockAdmin.setName(userName);
            mockAdmin.setPassword("CAD21ECC71B7633CA3BFE018822F24C143EFFA1505F030FCCF0E3695");
            return mockAdmin;
        });

    }
    
    @Test
    void testAuthenticate() {
        LoginModel login = new LoginModel();
        login.setPassword("admin");
        login.setUsername("admin123xxx");
        LoginResultModel result = authService.authenticate(login);
        assertNotNull(result);
        assertNotNull(result.getToken());

        assertTrue(jwtTokenUtil.validate(result.getToken())); 
        //assert token, the id should be -1
        assertEquals(-1L, jwtTokenUtil.getUserId(result.getToken()));        
    }

    @Test
    void testAuthenticateFailed() {
        LoginModel login = new LoginModel();
        login.setPassword("123456");
        login.setUsername("admin123xxx");
        LoginResultModel result = authService.authenticate(login);
        assertNull(result);
    }
}
