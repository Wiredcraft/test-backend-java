package com.wiredcraft.testbackend.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * author: yongchen
 * date: 2023/2/19
 */
@Service
public class JwtUserDetailsService implements UserDetailsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtUserDetailsService.class);

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.wiredcraft.testbackend.entity.User user = userService.getUserByName(username);
        if (user == null) {
            LOGGER.error("User not found, username={}", username);
            throw new UsernameNotFoundException("User does not exist");
        }
        return new User(user.getName(), user.getPassword(), getRoles());
    }

    /**
     * get role list
     *
     * @return
     */
    private List<SimpleGrantedAuthority> getRoles() {
        return new ArrayList<>();
    }

}