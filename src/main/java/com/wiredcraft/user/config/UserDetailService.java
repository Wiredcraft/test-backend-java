/*
package com.wiredcraft.user.config;

import com.wiredcraft.user.model.User;
import com.wiredcraft.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Arrays;
import java.util.Optional;

@Configuration
public class UserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findByName(s);
        if (optionalUser.isPresent()){
            // just a simple demo
            return new org.springframework.security.core.userdetails.User(s, "123456",
                    Arrays.asList(new SimpleGrantedAuthority("ROLE_user")));
        }else {
            throw  new UsernameNotFoundException("user not exist");
        }
    }
}
*/
