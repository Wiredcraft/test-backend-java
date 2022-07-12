package com.lyt.backend.models;

public enum ResponseMessage {
    SUCCEED_CREATED_USER("User successfully created"),
    SUCCEED_RETRIEVED_USER_INFO("User information successfully retrieved"),
    SUCCEED_UPDATED_USER_INFO("User information successfully updated"),
    REQUEST_FAIL("An internal error happened on your request");
    public final String message;

    ResponseMessage(String s) {
        this.message = s;
    }
}
