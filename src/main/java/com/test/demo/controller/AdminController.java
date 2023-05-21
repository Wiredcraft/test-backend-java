package com.test.demo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhangrucheng on 2023/5/21
 */
@RestController
@Slf4j
@RequestMapping(path = "/admin")
public class AdminController {

    @GetMapping(path = "/test")
    public String hello() {
        return "hello rucheng";
    }
}
