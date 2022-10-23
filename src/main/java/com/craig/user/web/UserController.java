package com.craig.user.web;

import java.util.List;

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
import com.craig.user.model.SimpleUserModel;
import com.craig.user.model.UserDetailModel;
import com.craig.user.model.UserModel;
import com.craig.user.model.UserQueryModel;
import com.craig.user.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/users")
@Tag(name = "User API", description = "User related rest APIs")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{userId}")
    @Operation(summary = "Get user detail", description = "get user detail with userId, get follow information with query param getFollower/getFollowing")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "get user successfuly"),
            @ApiResponse(responseCode = "404", description = "no user", content = @Content(examples = {})) })
    public ResponseEntity<UserDetailModel> getUserDetail(@PathVariable("userId") Long userId,
            @RequestParam(value = "getFollower", required = false) @Parameter(description = "set true to get this user's followers") Boolean getFollower,
            @RequestParam(value = "getFollowing", required = false) @Parameter(description = "set true to get this user's followings") Boolean getFollowing) {
        UserDetailModel model = userService.getUserDetail(userId, getFollower, getFollowing);
        if (model == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        return ResponseEntity.status(HttpStatus.OK).body(model);
    }

    @GetMapping
    @Operation(summary = "Get user list", description = "get user list with pagination")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "get user successfuly"),
            @ApiResponse(responseCode = "204", description = "nothing found", content = @Content(examples = {})) })
    public ResponseEntity<PageResult<UserModel>> getUsers(UserQueryModel query) {
        PageResult<UserModel> result = userService.getUsers(query);
        if (result.getTotal() == 0) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(result);
        }
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @PostMapping
    @Operation(summary = "Create user", description = "create user")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "user successfuly created"),
            @ApiResponse(responseCode = "204", description = "no user is created", content = @Content(examples = {})) })
    public ResponseEntity<UserModel> createUser(@RequestBody UserModel user) {
        UserModel result = userService.createUser(user);
        if (result.getId() != null && result.getId() > 0) {
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(result);
    }

    @PutMapping
    @Operation(summary = "Update user", description = "update user, if id provided, then do updating, if id is null, then do inserting")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "user successfuly created"),
            @ApiResponse(responseCode = "200", description = "user successfuly updated"),
            @ApiResponse(responseCode = "204", description = "no user is created or updated", content = @Content(examples = {})) })
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
    @Operation(summary = "Delete user", description = "logic delete user")
    @ApiResponses({
            @ApiResponse(responseCode = "404", description = "no user to delete"),
            @ApiResponse(responseCode = "204", description = "delete successfuly") })
    public ResponseEntity<Void> deleteUser(@PathVariable("userId") Long userId) {
        if (userService.deleteUser(userId)) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @GetMapping("/{userId}/nearby")
    @Operation(summary = "Get nearby users", description = "logic delete user")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "get nearby user successfuly"),
            @ApiResponse(responseCode = "404", description = "no nearby user", content = @Content(examples = {})) })
    public ResponseEntity<List<SimpleUserModel>> getNearbyUser(@PathVariable("userId") Long userId) {
        List<SimpleUserModel> results = userService.getNearbyUsers(userId);
        if (results == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        return ResponseEntity.status(HttpStatus.OK).body(results);
    }
}
