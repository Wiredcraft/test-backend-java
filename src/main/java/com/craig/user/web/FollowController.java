package com.craig.user.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.craig.user.model.FollowerModel;
import com.craig.user.model.SimpleUserModel;
import com.craig.user.model.UserDetailModel;
import com.craig.user.service.FollowerService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/users/{userId}")
@Tag(name = "Follower APIs", description = "Follower related APIs")
public class FollowController {

    @Autowired
    private FollowerService followerService;

    @GetMapping("/followers")
    @Operation(summary = "Get follower", security = {
            @SecurityRequirement(name = "bearer-key") }, description = "get the user's followers")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "get followers successfuly"),
            @ApiResponse(responseCode = "404", description = "no followers", content = @Content(examples = {})) })
    public ResponseEntity<List<SimpleUserModel>> getFollowers(@PathVariable("userId") Long userId) {
        List<SimpleUserModel> result = followerService.getFollowers(userId);
        if (CollectionUtils.isEmpty(result)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        }

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @PostMapping("/followers")
    @Operation(summary = "Add follower", security = {
            @SecurityRequirement(name = "bearer-key") }, description = "add current user to target user's followers list")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "follower add failed"),
            @ApiResponse(responseCode = "201", description = "follower add successfuly"),
            @ApiResponse(responseCode = "404", description = "user not exist", content = @Content(examples = {})) })
    public ResponseEntity<FollowerModel> addFollowers(@PathVariable("userId") Long userId) {
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) (SecurityContextHolder
                .getContext().getAuthentication());
        UserDetailModel currentUser = (UserDetailModel) (token.getPrincipal());

        Long currentUserId = currentUser.getId();
        FollowerModel result = followerService.addFollowers(userId, currentUserId);

        if (result == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        }

        if (result.getId() != null && result.getId() > 0) {
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(result);
    }

    @DeleteMapping("/followers")
    @Operation(summary = "Remove follower", security = {
            @SecurityRequirement(name = "bearer-key") }, description = "delete the relation between target userId and current user")
    @ApiResponses({
            @ApiResponse(responseCode = "404", description = "follower record not exist", content = @Content(examples = {})),
            @ApiResponse(responseCode = "204", description = "delete successfuly", content = @Content(examples = {})) })
    public ResponseEntity<Void> removeFollowers(@PathVariable("userId") Long userId) {

        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) (SecurityContextHolder
                .getContext().getAuthentication());
        UserDetailModel currentUser = (UserDetailModel) (token.getPrincipal());

        Long currentUserId = currentUser.getId();
        if (followerService.deleteFollower(userId, currentUserId)) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @GetMapping("/followings")
    @Operation(summary = "Get user's followings", security = {
            @SecurityRequirement(name = "bearer-key") }, description = "get user's following list")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "get followings successfuly"),
            @ApiResponse(responseCode = "404", description = "no followings", content = @Content(examples = {})) })
    public ResponseEntity<List<SimpleUserModel>> getFollowings(@PathVariable("userId") Long userId) {
        List<SimpleUserModel> result = followerService.getFollowings(userId);
        if (CollectionUtils.isEmpty(result)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        }

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
