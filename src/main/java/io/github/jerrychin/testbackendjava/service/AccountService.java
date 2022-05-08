package io.github.jerrychin.testbackendjava.service;

import io.github.jerrychin.testbackendjava.dto.AccountDTO;
import io.github.jerrychin.testbackendjava.entity.Account;
import io.github.jerrychin.testbackendjava.repository.AccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Slf4j
@Service
public class AccountService {
    private final AccountRepository repository;
    private final PasswordEncoder encoder;

    public AccountService(AccountRepository repository, PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
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
}
