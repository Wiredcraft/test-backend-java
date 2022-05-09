package io.github.jerrychin.testbackendjava.model.vo;

public class ResponseVO {
    private final String message;

    public ResponseVO(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
