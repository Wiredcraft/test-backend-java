package com.zhangyongxin.demo.controller;

import com.zhangyongxin.demo.common.Result;
import com.zhangyongxin.demo.common.ResultGenerator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * hello world demo
 * @author zhangyongxin
 * @date 2022/3/17 20:35
 */
@RequestMapping("test")
@RestController
public class HelloWorldController {

    @GetMapping("/hello")
    public Result<String> helloWorld() {
        return ResultGenerator.genSuccessResult("Hello World!我爱中国");
    }
}
