package com.craig.user.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.craig.user.model.LoginModel;
import com.craig.user.model.LoginResultModel;
import com.craig.user.model.UserDetailModel;
import com.craig.user.service.AuthService;
import com.craig.user.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;


@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/authenticate")
    @Operation(summary = "User login", description = "login with username and password")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "login successfuly"),
            @ApiResponse(responseCode = "401", description = "login failed", content = @Content(examples = {})) })
    public ResponseEntity<LoginResultModel> authenticate(@RequestBody LoginModel request) {
        LoginResultModel model = authService.authenticate(request);

        if (model == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(model);
    }
}
