package com.w.t.module.controller;

import com.w.t.common.core.response.R;
import com.w.t.common.core.web.BaseController;
import com.w.t.module.entity.User;
import com.w.t.module.entity.dto.UserDTO;
import com.w.t.module.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author zhangxiang
 * @date 2023/02/09
 */

@Api(value = "user api")
@RestController
@RequestMapping("/users")
public class UserController extends BaseController {

    private final IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @ApiOperation("create a user")
    @PostMapping
    public R<Integer> createUser(@RequestBody UserDTO userDTO) {
        User user=new User();
        BeanUtils.copyProperties(userDTO,user);
        int result = userService.createUser(user);
        return R.success(result);
    }

    @ApiOperation("get user by id")
    @GetMapping("{id}")
    public R<User> getUserById(@PathVariable long id) {
        User user = userService.getUserById(id);
        return R.success(user);
    }

    @ApiOperation("update user by id")
    @PutMapping("{id}")
    public R<Integer> updateUserById(@PathVariable long id, @RequestBody UserDTO userDTO) {
        User user=new User();
        BeanUtils.copyProperties(userDTO,user);
        return R.success(userService.updateUserById(id, user));
    }

    @ApiOperation("delete user by user id")
    @DeleteMapping("/{userId}")
    public R<Integer> deleteUser(@PathVariable("userId") Long userId) {
        return R.success(userService.removeUserById(userId));
    }

    @ApiOperation("get all users")
    @GetMapping
    public R<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return R.success(users);
    }
}
