package com.test.demo.controller;

import com.test.demo.entity.UserDo;
import com.test.demo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;
import java.util.UUID;

/**
 * @author zhangrucheng on 2023/5/21
 */
@Controller
@Slf4j
public class LoginController {

    @Autowired
    private UserService userService;

    @RequestMapping("/login")
    public String login() {
        // 登录失败跳转回登录页面
        return "login";
    }

    @RequestMapping("/index")
    public String index() {
        // 登录失败跳转回登录页面
        return "redirect:/index.html";
    }


    @RequestMapping("/success")
    public String successLogin() {
        return "main";
    }

    @RequestMapping("/toError")
    public String toError() {
        return "error";
    }
}
