package io.github.jerrychin.testbackendjava.exception;

import org.springframework.http.HttpStatus;

public class RestApiException extends RuntimeException {
    private HttpStatus httpStatus;
    public RestApiException(HttpStatus httpStatus, String message){
        super(message);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
