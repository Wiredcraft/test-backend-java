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
        return null;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }

    public String getToken() {
        return token;
    }
}
