package com.lyt.backend.models;

import io.swagger.annotations.ApiModelProperty;

public class Response {
    @ApiModelProperty(required = true)
    private boolean succeed;
    @ApiModelProperty(required = true, value = "Message of remote server given")
    private String message;

    @ApiModelProperty(value = "User returned")
    private UserInfoDTO data;

    public boolean isSucceed() {
        return succeed;
    }


    public String getMessage() {
        return message;
    }

    public static  Response ofSucceed(String message, UserInfoDTO data) {
        Response response = new Response();
        response.message = message;
        response.succeed = true;
        response.data = data;
        return response;
    }

    public static Response ofFailure(String message) {
        Response response = new Response();
        response.message = message;
        response.succeed = false;
        return response;
    }

    public UserInfoDTO getData() {
        return data;
    }
}
