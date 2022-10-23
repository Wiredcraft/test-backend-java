package com.craig.user.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.craig.user.model.FollowerModel;
import com.craig.user.model.SimpleUserModel;
import com.craig.user.service.UserService;

@RestController
@RequestMapping("/users/{userId}")
public class FollowController {

    @Autowired
    private UserService userService;
    
    @GetMapping("/followers")
    public ResponseEntity<List<SimpleUserModel>> getFollowers(@PathVariable("userId") Long userId) {
        List<SimpleUserModel> result = userService.getFollowers(userId);
        if (result == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        }

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @PostMapping("/followers")
    public ResponseEntity<FollowerModel> addFollowers(@PathVariable("userId") Long userId) {
        Long currentUserId = 0L; // finish here after add auth logic and user context
        FollowerModel result = userService.addFollowers(userId, currentUserId);

        if (result == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        }

        if (result.getId() != null && result.getId() > 0) {
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(result);
    }
    
    @DeleteMapping("/followers")
    public ResponseEntity<FollowerModel> removeFollowers(@PathVariable("userId") Long userId) {
        Long currentUserId = 0L; // finish here after add auth logic and user context

        if (userService.deleteFollower(userId, currentUserId)) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @GetMapping("/followings")
    public ResponseEntity<List<SimpleUserModel>> getFollowings(@PathVariable("userId") Long userId) {
        List<SimpleUserModel> result = userService.getFollowings(userId);
        if (result == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        }

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
