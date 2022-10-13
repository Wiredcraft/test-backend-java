package com.wiredcraft.user.controller;

import com.wiredcraft.user.model.User;
import com.wiredcraft.user.model.vo.UserVo;
import com.wiredcraft.user.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    @ResponseBody
    //@PreAuthorize("hasAnyRole('user')")
    ResponseEntity<Object> create(@RequestBody UserVo vo){
        try {
            User user = new User();
            BeanUtils.copyProperties(vo, user, "id");
            userService.createUser(user);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("call back end error");
        }
        return ResponseEntity.ok().body("success");
    }

    @DeleteMapping(value = "/{id}")
    @ResponseBody
    //@PreAuthorize("hasAnyRole('user')")
    ResponseEntity<Object> delete(@PathVariable long id){
        try {
            userService.deleteUser(id);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("call back end error");
        }
        return ResponseEntity.ok("success");
    }

    @PutMapping
    @ResponseBody
    //@PreAuthorize("hasAnyRole('user')")
    ResponseEntity<Object> update(@RequestBody UserVo vo){
        if(null == vo.getId()){
            return ResponseEntity.badRequest().body("id can't be null");
        }
        try {
            User user = new User();
            BeanUtils.copyProperties(vo, user);
            userService.updateUser(user);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("call back end error");
        }
        return ResponseEntity.ok("success");
    }

    @GetMapping(value = "/{id}")
    @ResponseBody
    //@PreAuthorize("hasAnyRole('user')")
    ResponseEntity<Object> get(@PathVariable long id){
        Optional<User> optionalUser;
        try {
            optionalUser = userService.getUserById(id);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("call back end error");
        }
        if(optionalUser.isPresent()){
            return ResponseEntity.ok(optionalUser.get());
        }else{
            return ResponseEntity.status(201).body("can't find this user");
        }

    }
}
