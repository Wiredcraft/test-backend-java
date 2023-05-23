package me.solution.service.auth;

import me.solution.model.domain.User;
import me.solution.security.model.LoginUser;
import me.solution.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * customized UserDetailService implements Spring-Security #{@link UserDetailsService}
 *
 * @author davincix
 * @since 2023/5/20 19:26
 */
@Service
public class MyUserDetailService implements UserDetailsService {
    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // get user by username
        // if user not exist, throw an exception
        User user = userService.getUserByName(username, true);
        Optional.ofNullable(user)
                .orElseThrow(() -> new RuntimeException("用户名或密码错误"));

        // TODO: 2023/5/21 roles and permits

        // wrap the user info to UserDetails
        return new LoginUser(user);
    }
}
