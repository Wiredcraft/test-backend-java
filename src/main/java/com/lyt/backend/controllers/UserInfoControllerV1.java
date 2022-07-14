package com.lyt.backend.controllers;

import com.lyt.backend.models.Response;
import com.lyt.backend.models.ResponseMessage;
import com.lyt.backend.service.UserInfoService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/v1")
@Api(
        tags = {"Get or Change detailed information of user"}
)
public class UserInfoControllerV1 {
    @Autowired
    private UserInfoService userInfoService;


    @Operation(
            method = "GET",
            summary = "Retrieve detailed information of current user",
            responses = {@ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Response.class))})}
    )
    @RequestMapping(value = "user", method = {RequestMethod.GET})
    public ResponseEntity<Response> getUser() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(Response.ofSucceed(ResponseMessage.SUCCEED_RETRIEVED_USER_INFO.message, userInfoService.getUserInfo(userDetails.getUsername())));
    }

    @Operation(
            method = "GET",
            summary = "Retrieve detailed information of assigned user",
            parameters = {
                    @Parameter(name = "name", in = ParameterIn.PATH, required = true, description = "Name of user you want to get info.")
            },
            responses = {@ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Response.class))})}
    )
    @RequestMapping(value = "user/{name}", method = {RequestMethod.GET})
    public ResponseEntity<Response> getUser(@PathVariable String name) {
        return ResponseEntity.ok(Response.ofSucceed(ResponseMessage.SUCCEED_RETRIEVED_USER_INFO.message, userInfoService.getUserInfo(name)));
    }

    @Operation(
            method = "POST",
            summary = "Update description and address of user",
            parameters = {
                    @Parameter(name = "description", in = ParameterIn.QUERY, description = "the description of user"),
                    @Parameter(name = "address", in = ParameterIn.QUERY, description = "the address of user")

            },
            responses = {@ApiResponse(responseCode = "201", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Response.class))}),
                    @ApiResponse(responseCode = "401")}
    )
    @RequestMapping(value = "user", method = {RequestMethod.POST})
    public ResponseEntity<Response> updateUser(@RequestParam(required = false) String address, @RequestParam(required = false) String description) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (address != null || description != null) {
            userInfoService.updateAddressAndDescription(userDetails.getUsername(), address, description);
        }
        return ResponseEntity.created(URI.create("./v1/users/"))
                .body(Response.ofSucceed(ResponseMessage.SUCCEED_UPDATED_USER_INFO.message, null));
    }
}
