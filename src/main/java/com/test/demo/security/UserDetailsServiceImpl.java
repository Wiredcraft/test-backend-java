package com.test.demo.security;

import com.test.demo.entity.UserDo;
import com.test.demo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zhangrucheng on 2023/5/22
 */

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserDo userDo = new UserDo();
        userDo.setName(username);
        List<UserDo> userDos = userMapper.selectUserByCondition(userDo);

        if (userDos == null || userDos.isEmpty()) {
            throw new UsernameNotFoundException("User name doesn't exist!");
        }
        userDo = userDos.get(0);

        return new User(username, userDo.getPassword(), AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER"));
    }
}
