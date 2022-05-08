package io.github.jerrychin.testbackendjava.dto;

public class AccessTokenDTO {

    /**
     * short-lived JWT token for user authentication.
     */
    private final String token;

    public AccessTokenDTO(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

}
