package io.github.jerrychin.testbackendjava.service;

import io.github.jerrychin.testbackendjava.dto.AccessTokenDTO;
import io.github.jerrychin.testbackendjava.dto.AccountDTO;
import io.github.jerrychin.testbackendjava.entity.Account;
import io.github.jerrychin.testbackendjava.exception.RestApiException;
import io.github.jerrychin.testbackendjava.repository.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;

@Service
public class AccountService {
    private final static Logger log = LoggerFactory.getLogger(AccountService.class);

    private final AccountRepository repository;

    private final PasswordEncoder encoder;

    private final DaoAuthenticationProvider provider;

    private final JWTService jwtService;

    public AccountService(AccountRepository repository, JWTService jwtService) {
        this.repository = repository;
        this.encoder = new BCryptPasswordEncoder();
        this.jwtService = jwtService;

        provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(encoder);
        provider.setUserDetailsService(username -> {
            log.trace("loading user: {}", username);

            try {
                Account account = findAccount(username);

                log.trace("user loaded: {}", username);
                return new User(username, account.getPassword(), Collections.emptyList());
            } catch (Exception exception) {
               throw new UsernameNotFoundException("account or password mismatch!");
            }
        });
    }

    @Transactional
    public Long saveAccount(AccountDTO accountDTO) {
        Account account = new Account();
        account.setAccount(accountDTO.getAccount());
        account.setPassword(encoder.encode(accountDTO.getPassword()));
        account = repository.save(account);

        return account.getId();
    }

    public boolean existsUserByAccount(String account) {
        return repository.existsAccountByAccount(account);
    }

    public AccessTokenDTO authenticate(AccountDTO accountDTO) {
        log.trace("authenticating account, account: {}", accountDTO.getAccount());

        Authentication authentication = provider.authenticate(
                new UsernamePasswordAuthenticationToken(accountDTO.getAccount(), accountDTO.getPassword()));

        log.trace("account authenticated, account: {}", accountDTO.getAccount());

        String token = jwtService.generateToken(authentication.getName());
        log.trace("token generated, account: {}", accountDTO.getAccount());

        return new AccessTokenDTO(token);
    }

    public Account findAccount(Long accountId) {
        return repository.findById(accountId).orElseThrow(() ->
                new RestApiException(HttpStatus.BAD_REQUEST, "account not found!"));
    }

    public Account findAccount(String account) {
        return repository.findAccountByAccount(account).orElseThrow(() ->
                new RestApiException(HttpStatus.BAD_REQUEST, "account not found!"));
    }


}
