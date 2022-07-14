package com.lyt.backend;

import com.lyt.backend.service.LoginInfoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

@Testable
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = MainTest.class)
public class TestLoginInfoService {
    @Autowired
    private LoginInfoService loginInfoService;

    @Autowired
    private PasswordEncoder encoder;

    @Test
    public void testCRUD() {
        Assertions.assertTrue(loginInfoService.register("马六字", "abcdefg"));
        Assertions.assertTrue(loginInfoService.register("小七", "abcdefg"));
        Assertions.assertFalse(loginInfoService.register("马六字", "abcdefg"));
        UserDetails mlz = Assertions.assertDoesNotThrow(() -> loginInfoService.loadUserByUsername("马六字"));
        Assertions.assertEquals(mlz.getUsername(), "马六字");
        Assertions.assertTrue(encoder.matches("abcdefg", mlz.getPassword()));
        Assertions.assertThrows(UsernameNotFoundException.class, () -> loginInfoService.loadUserByUsername("王五"));
        Assertions.assertDoesNotThrow(() -> loginInfoService.changePassword("小七", "efghjk"));
        Assertions.assertTrue(encoder.matches("efghjk", Assertions.assertDoesNotThrow(() -> loginInfoService.loadUserByUsername("小七")).getPassword()));
        Assertions.assertDoesNotThrow(() -> loginInfoService.deRegister("马六字"));
        Assertions.assertThrows(UsernameNotFoundException.class, () -> loginInfoService.loadUserByUsername("马六字"));
    }
}
