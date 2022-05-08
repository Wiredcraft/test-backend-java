package io.github.jerrychin.testbackendjava.service;

import io.github.jerrychin.testbackendjava.entity.Account;
import io.github.jerrychin.testbackendjava.repository.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collections;

/**
 * Admin 用户信息服务数据源，根据输入用户名返回 UserDetails 类型的元素，供 <code>DaoAuthenticationProvider</code> 进一步验证登录用户身份。
 */
@Component
public class AdminUserDetailsService implements UserDetailsService {
    private static final Logger log = LoggerFactory.getLogger(AdminUserDetailsService.class);

    private final AccountRepository repository;

    public AdminUserDetailsService(AccountRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        log.info("loading user: {}", username);

        Account account = repository.findAccountByAccount(username).orElseThrow(() ->
                new UsernameNotFoundException("account or password mismatch!"));

        log.info("user loaded: {}", username);
        return new User(username, account.getPassword(), Collections.emptyList());
    }
}
