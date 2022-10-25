package com.craig.user.web;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.craig.user.TestApplication;
import com.craig.user.model.LoginModel;
import com.craig.user.model.LoginResultModel;
import com.craig.user.service.AuthService;

@SpringBootTest(classes = TestApplication.class)
public class AuthControllerTest {

    @MockBean
    private AuthService authService;

    @Autowired
    private AuthController authController;

    @BeforeEach
    void setUp() throws Exception {
        Mockito.when(authService.authenticate(any())).thenAnswer(a->{
            LoginModel model = a.getArgument(0);
            if(model.getUsername().equals("admin")){
                LoginResultModel result = new LoginResultModel();
                result.setToken("token here");
                return result;
            }
            return null;
        });
    }
    
    @Test
    void testAuthenticate() {
        LoginModel model = new LoginModel();
        model.setPassword("admin");
        model.setUsername("admin");
        ResponseEntity<LoginResultModel> result = authController.authenticate(model);
        assertNotNull(result.getBody());
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("token here", result.getBody().getToken());
    }

    @Test
    void testAuthenticateFailed() {
        LoginModel model = new LoginModel();
        model.setPassword("admin");
        model.setUsername("admin123");
        ResponseEntity<LoginResultModel> result = authController.authenticate(model);
        assertNull(result.getBody());
        assertEquals(HttpStatus.UNAUTHORIZED, result.getStatusCode());
    }
}
