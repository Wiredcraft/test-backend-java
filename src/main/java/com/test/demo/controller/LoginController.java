package com.test.demo.controller;

import com.test.demo.entity.UserDo;
import com.test.demo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * @author zhangrucheng on 2023/5/21
 */
@RestController
@Slf4j
public class LoginController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public String login(@RequestParam("userName") String userName,
                                        @RequestParam("password") String password){
        log.info("user [{}] is trying login ...", userName);
        return "login";
    }

}
