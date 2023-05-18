package com.jiang.test.backend.controller;

import com.jiang.test.backend.annotation.Authorized;
import com.jiang.test.backend.constant.ApiConstants;
import com.jiang.test.backend.entity.User;
import com.jiang.test.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(ApiConstants.COMMON_URL)
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * getUser
     * @param id userId
     * @return User user信息
     * @description 根据id获取user
     */
    @GetMapping(ApiConstants.GET_URL)
    @Authorized
    public ResponseEntity<User> getUser(@PathVariable String id){
        Optional<User> user = userService.getUserById(Integer.parseInt(id));
        return user.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * addUser
     * @param user user信息
     * @return user user信息
     * @description 添加user
     */
    @PostMapping(ApiConstants.POST_URL)
    @Authorized
    public ResponseEntity<User> addUser(@RequestBody User user){
        User createdUser = userService.addUser(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    /**
     * updateUser
     * @param user user信息
     * @return user user信息
     * @description 更新user
     */
    @PutMapping(ApiConstants.UPDATE_URL)
    @Authorized
    public ResponseEntity<User> updateUser(@RequestBody User user){
        Optional<User> existingUser = userService.getUserById(user.getId());
        if (existingUser.isPresent()) {
            User updatedUser = userService.updateUser(user);
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * updateUser
     * @param id userId
     * @return void
     * @description 根据userId 删除User
     */
    @DeleteMapping(ApiConstants.DELETE_URL)
    @Authorized
    public ResponseEntity<Void> delUser(@RequestParam int id){
        Optional<User> existingUser = userService.getUserById(id);
        if (existingUser.isPresent()) {
            userService.deleteUserById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
