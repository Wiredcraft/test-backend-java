package com.wiredcraft.testbackend.controller;

import com.wiredcraft.testbackend.entity.JwtResponse;
import com.wiredcraft.testbackend.entity.Result;
import com.wiredcraft.testbackend.entity.ResultsCode;
import com.wiredcraft.testbackend.entity.param.JwtRequest;
import com.wiredcraft.testbackend.service.JwtUserDetailsService;
import com.wiredcraft.testbackend.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * authentication api
 */
@RestController
@Validated
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    /**
     * get token
     */
    @PostMapping("auth")
    public Result<?> createAuthenticationToken(@RequestBody @Valid JwtRequest request) {
        try {
            String username = request.getUsername();
            String password = request.getPassword();
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(username, password);
            authenticationManager.authenticate(auth);

            UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(username);
            String token = jwtTokenUtil.generateToken(userDetails);
            return Result.success(new JwtResponse(token));
        } catch (BadCredentialsException exception) {
            return Result.error(ResultsCode.WRONG_NAME_PASS.getCode(), ResultsCode.WRONG_NAME_PASS.getMessage());
        } catch (Exception exception) {
            return Result.error(ResultsCode.UNAUTHORIZED.getCode(), exception.getMessage());
        }
    }

}