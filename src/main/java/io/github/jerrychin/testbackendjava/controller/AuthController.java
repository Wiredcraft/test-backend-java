package io.github.jerrychin.testbackendjava.controller;

import io.github.jerrychin.testbackendjava.dto.AccountDTO;
import io.github.jerrychin.testbackendjava.exception.RestApiException;
import io.github.jerrychin.testbackendjava.service.AccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
@Api(tags="Authentication")
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AccountService service;

    public AuthController(AccountService service) {
        this.service = service;
    }

    @ApiOperation("User Signup")
    @PostMapping("/signup")
    @Transactional
    public void signup(@RequestBody AccountDTO accountDTO) {

        if (StringUtils.isBlank(accountDTO.getAccount())) {
            throw new RestApiException(HttpStatus.BAD_REQUEST, "account is required.");
        }

        if (StringUtils.isBlank(accountDTO.getPassword())) {
            throw new RestApiException(HttpStatus.BAD_REQUEST, "password is required.");
        }

        if (service.existsUserByAccount(accountDTO.getAccount())) {
            throw new RestApiException(HttpStatus.BAD_REQUEST, "account existed, please login.");
        }

        service.saveAccount(accountDTO);
    }
}