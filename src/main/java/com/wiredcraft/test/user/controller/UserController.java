package com.wiredcraft.test.user.controller;

import com.wiredcraft.test.user.model.req.UserReq;
import com.wiredcraft.test.user.model.vo.UserVO;
import com.wiredcraft.test.user.service.UserService;
import com.wiredcraft.test.user.tool.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * User API
 * 该类一般用于暴露给前端或者23方，2方目前主流调用方式为RPC
 * 命名方式具体看规范，有一些用Controller，有一些用Api
 * 暴露的uri也是具体看规范，目前有两种方式
 * 1 根据 GET POST PUT DELETE 来区分请求，例如uri：/api/user
 * 2 在一些企业级实战中，为了方便维护代码，不需要严格遵守RESTful API，可通过在uri上直接体现一些关键词，增加可读性，
 * 例如uri：/api/user/get/{id}
 */
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * List users
     *
     * @return users
     */
    @GetMapping("/page")
    public JsonResult<List<UserVO>> list() {
        List<UserVO> list = userService.list();
        return JsonResult.success(list);
    }

    /**
     * Get user by user ID
     *
     * @param id user ID
     * @return user info
     */
    @GetMapping("/get/{id}")
    public JsonResult<UserVO> get(@PathVariable int id) {
        UserVO userVO = userService.get(id);
        return JsonResult.success(userVO);
    }

    /**
     * Create user
     *
     * @param userReq user form
     * @return user ID
     */
    @PostMapping("/create")
    public JsonResult<Void> create(@RequestBody UserReq userReq) {
        userService.create(userReq);
        return JsonResult.success();
    }

    /**
     * Update user
     *
     * @param userReq user form
     * @return user ID
     */
    @PutMapping("/update")
    public JsonResult<Void> update(@RequestBody UserReq userReq) {
        userService.update(userReq);
        return JsonResult.success();
    }

    /**
     * Delete by user ID
     *
     * @param id User ID
     * @return
     */
    @DeleteMapping("/delete/{id}")
    public JsonResult<Void> delete(@PathVariable int id) {
        userService.delete(id);
        return JsonResult.success();
    }

}