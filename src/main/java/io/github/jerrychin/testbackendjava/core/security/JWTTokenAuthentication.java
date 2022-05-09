package io.github.jerrychin.testbackendjava.core.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;

public class JWTTokenAuthentication extends AbstractAuthenticationToken {

    private final String token;

    public JWTTokenAuthentication(String token) {
        super(null);
        this.token = token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    public String getToken() {
        return token;
    }
}
