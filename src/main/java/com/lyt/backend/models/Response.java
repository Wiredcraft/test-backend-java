package com.lyt.backend.models;

public class Response {
    private boolean succeed;
    private String message;

    private User data;

    public boolean isSucceed() {
        return succeed;
    }


    public String getMessage() {
        return message;
    }

    public static  Response ofSucceed(String message, User data) {
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

    public User getData() {
        return data;
    }
}
