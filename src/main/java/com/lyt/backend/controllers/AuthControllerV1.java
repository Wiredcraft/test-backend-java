package com.lyt.backend.controllers;

import com.lyt.backend.models.Response;
import com.lyt.backend.models.ResponseMessage;
import com.lyt.backend.service.LoginInfoService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.net.URI;

@Controller
@Api(
    tags = {"User registration, changing password, and deregistration"}
)
@RequestMapping("/v1")
public class AuthControllerV1 {
    @Resource
    private LoginInfoService loginInfoService;

    @Operation(
            method = "POST",
            summary = "register a new user on server",
            parameters = {
                    @Parameter(name = "username", in = ParameterIn.QUERY, required = true, description = "name of user to create"),
                    @Parameter(name = "password", in = ParameterIn.QUERY, required = true, description = "the password of user")
            },
            responses = {@ApiResponse(responseCode = "201", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Response.class))})}
    )
    @RequestMapping(value = "/register", method = {RequestMethod.POST})
    @ResponseBody
    public ResponseEntity<Response> register(@RequestParam() String username, @RequestParam() String password) {
        loginInfoService.register(username, password);
        return ResponseEntity.created(URI.create("/v1/users/")).body(Response.ofSucceed(ResponseMessage.SUCCEED_CREATED_USER.message, null));
    }


    @Operation(
            method = "PATCH",
            summary = "change the password of current user logged in",
            parameters = {
                    @Parameter(name = "newPassword", in = ParameterIn.QUERY, required = true, description = "the new password of user")
            },
            responses = {@ApiResponse(responseCode = "201", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Response.class))}),
                    @ApiResponse(responseCode = "401")}
    )
    @RequestMapping(value = "/user", method = {RequestMethod.PATCH})
    @ResponseBody
    public ResponseEntity<Response> changePassword(@RequestParam() String newPassword) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        loginInfoService.changePassword(userDetails.getUsername(), newPassword);
        return ResponseEntity.created(URI.create("/v1/users")).body(Response.ofSucceed(ResponseMessage.SUCCEED_UPDATED_USER_INFO.message, null));
    }

    @Operation(
            method = "POST",
            summary = "unregister the user currently logged in",
            responses = {@ApiResponse(responseCode = "204"),
                    @ApiResponse(responseCode = "401")}
    )
    @RequestMapping(value = "/user", method = {RequestMethod.DELETE})
    @ResponseBody
    public ResponseEntity deregister(HttpServletResponse response) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //invalidate cookie
        Cookie cookie = new Cookie("JSESSIONID", "");
        response.addCookie(cookie);
        loginInfoService.deRegister(userDetails.getUsername());
        return ResponseEntity.noContent().build();
    }

    @Operation(
            method = "GET",
            summary = "logout and clear cookie",
            responses = {@ApiResponse(responseCode = "200"),
                    @ApiResponse(responseCode = "401")}    )
    @RequestMapping(value = "/logout", method = {RequestMethod.GET})
    public ResponseEntity logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("JSESSIONID", "");
        response.addCookie(cookie);
        return ResponseEntity.ok("You have logged out!");

    }
}
