package io.github.jerrychin.testbackendjava.service;

import io.github.jerrychin.testbackendjava.SampleBaseTestCase;
import io.github.jerrychin.testbackendjava.dto.AccountDTO;
import io.github.jerrychin.testbackendjava.entity.Account;
import io.github.jerrychin.testbackendjava.repository.AccountRepository;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.authentication.BadCredentialsException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AccountServiceTest extends SampleBaseTestCase {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private JWTService jwtService;

    @InjectMocks
    private AccountService accountService;

    @Test
    public void whenPassingCorrectAccount_thenAuthenticateSuccess() {
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setAccount("123");
        accountDTO.setPassword("123456");

        Account account = new Account();
        account.setAccount(accountDTO.getAccount());
        account.setPassword(accountService.encoder().encode(accountDTO.getPassword()));
        when(accountRepository.findAccountByAccount(accountDTO.getAccount())).thenReturn(Optional.of(account));

        accountService.authenticate(accountDTO);

        verify(jwtService).generateToken(any());
    }

    @Test
    public void whenPassingIncorrectPassword_thenAuthenticateFail() {
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setAccount("123");
        accountDTO.setPassword("123456");

        Account account = new Account();
        account.setAccount(accountDTO.getAccount());

        // let's use a different password
        account.setPassword(accountService.encoder().encode("654321"));
        when(accountRepository.findAccountByAccount(accountDTO.getAccount())).thenReturn(Optional.of(account));

        assertThatThrownBy(() -> {
            accountService.authenticate(accountDTO);
        }).isInstanceOf(BadCredentialsException.class);
    }

    @Test
    public void whenPassingMissingAccount_thenAuthenticateFail() {
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setAccount("123");
        accountDTO.setPassword("123456");

        when(accountRepository.findAccountByAccount(accountDTO.getAccount())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> {
            accountService.authenticate(accountDTO);
        }).isInstanceOf(BadCredentialsException.class);
    }
}