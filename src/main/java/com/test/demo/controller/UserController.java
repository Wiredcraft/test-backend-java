package com.test.demo.controller;

import com.test.demo.entity.UserDo;
import com.test.demo.exception.UnAuthorizedException;
import com.test.demo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * @author zhangrucheng on 2023/5/21
 */
@RestController
@RequestMapping(path = "/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * getUser
     * @param id userId
     * @return User user信息
     * @description 根据id获取user
     */
    @GetMapping("/get")
    public ResponseEntity<UserDo> getUser(@RequestParam int id){
        log.info("begin to get user with id [{}]", id);
        Optional<UserDo> user = userService.getUserById(id);
        user.ifPresent(userDo -> validateAuth(userDo.getName(), userDo.getPassword()));
        return user.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/get/name")
    public ResponseEntity<UserDo> getUserByName(@RequestParam String name){
        log.info("begin to get user with name [{}]", name);
        UserDo user = userService.getUserByName(name);
        if (user != null) {
            validateAuth(user.getName(), user.getPassword());
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * addUser
     * @param userDo user信息
     * @return user user信息
     * @description 添加user
     */
    @PostMapping("/add")
    public ResponseEntity<UserDo> addUser(@RequestBody UserDo userDo){
        userService.registerUser(userDo);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * updateUser
     * @param userDo user信息
     * @return user user信息
     * @description 更新user
     */
    @PutMapping("/update")
    public ResponseEntity<UserDo> updateUser(@RequestBody UserDo userDo){
        Optional<UserDo> existingUser = userService.getUserById(userDo.getId());
        if (existingUser.isPresent()) {
            existingUser.ifPresent(user -> validateAuth(user.getName(), user.getPassword()));
            userService.updateUser(userDo);
            return new ResponseEntity<>(HttpStatus.OK);
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
    @DeleteMapping("/delete")
    public ResponseEntity<Void> delUser(@RequestParam int id){
        Optional<UserDo> existingUser = userService.getUserById(id);
        if (existingUser.isPresent()) {
            existingUser.ifPresent(user -> validateAuth(user.getName(), user.getPassword()));
            userService.deleteUser(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //TODO use AOP to do this
    private void validateAuth(String name, String passwd) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        UserDetails userDetails = (UserDetails) principal;
        if (!name.equals(userDetails.getUsername()) && !passwd.equals(passwordEncoder.encode(userDetails.getPassword()))) {
            throw new UnAuthorizedException("Unauthorized Operation !");
        }
    }

}
