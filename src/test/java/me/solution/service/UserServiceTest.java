package me.solution.service;

import me.solution.model.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

/**
 * test for #{@link UserService}
 *
 * @author davincix
 * @since 2023/5/20 19:08
 */
@SpringBootTest
public class UserServiceTest {
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void testGetUserByName() {
        User user = userService.getUserByName("cyj", true);
        Optional.ofNullable(user)
                .ifPresent(System.out::println);
    }

    @Test
    public void testSaveUser() {
        String encodedPasswd = passwordEncoder.encode("1234");
        User saver = User.builder()
                .name("cyj3")
                .passwd(encodedPasswd)
                .build();
        userService.saveUser(saver);
        System.out.println("the fill-back id is:" + saver.getId());
    }

    @Test
    public void testSoftDelUser() {
        userService.softDelById(1L);
        User user = userService.getUserById(1L);
        Optional.ofNullable(user)
                .ifPresent(System.out::println);
    }
}
