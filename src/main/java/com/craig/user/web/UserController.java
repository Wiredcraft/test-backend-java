package com.craig.user.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.craig.user.model.PageResult;
import com.craig.user.model.UserDetailModel;
import com.craig.user.model.UserModel;
import com.craig.user.model.UserQueryModel;
import com.craig.user.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{userId}")
    public ResponseEntity<UserDetailModel> getUserDetail(@PathVariable("userId") Long userId,
            @RequestParam(value = "getFollower", required = false) Boolean getFollower) {
        UserDetailModel model = userService.getUserDetail(userId, getFollower);
        if (model == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        return ResponseEntity.status(HttpStatus.OK).body(model);
    }

    @GetMapping
    public ResponseEntity<PageResult<UserModel>> getUsers(UserQueryModel query) {
        PageResult<UserModel> result = userService.getUsers(query);
        if (result.getTotal() == 0) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(result);
        }
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @PostMapping
    public ResponseEntity<UserModel> createUser(@RequestBody UserModel user) {
        UserModel result = userService.createUser(user);
        if (result.getId() != null && result.getId() > 0) {
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(result);
    }

    @PutMapping
    public ResponseEntity<UserModel> updateUser(@RequestBody UserModel user) {
        UserModel result;
        HttpStatus responseStatus = null;
        if (user.getId() == null) {
            result = userService.createUser(user);
            if (result.getId() != null && result.getId() > 0) {
                responseStatus = HttpStatus.CREATED;
            }
        } else {
            result = userService.updateUser(user);
            if (result != null && result.getId() != null && result.getId() > 0) {
                responseStatus = HttpStatus.OK;
            }
        }
        if (responseStatus == null) {
            // neither create or update is failed
            responseStatus = HttpStatus.NO_CONTENT;
        }
        return ResponseEntity.status(responseStatus).body(result);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable("userId") Long userId) {
        if (userService.deleteUser(userId)) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
}
