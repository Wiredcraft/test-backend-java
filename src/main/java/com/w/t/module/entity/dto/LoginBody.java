package com.w.t.module.entity.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginBody {

    private String username;

    private String password;

}
