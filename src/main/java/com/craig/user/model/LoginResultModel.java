package com.craig.user.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class LoginResultModel {
    @Schema(description = "token for authentication, type is JWT")
    private String token;
}
