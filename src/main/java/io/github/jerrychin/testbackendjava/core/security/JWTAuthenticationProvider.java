package io.github.jerrychin.testbackendjava.core.security;

import com.auth0.jwt.interfaces.DecodedJWT;
import io.github.jerrychin.testbackendjava.entity.Account;
import io.github.jerrychin.testbackendjava.service.AccountService;
import io.github.jerrychin.testbackendjava.service.JWTService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class JWTAuthenticationProvider implements AuthenticationProvider {
	private static final Logger log = LoggerFactory.getLogger(JWTAuthenticationFilter.class);

	private final JWTService jwtService;
	private final AccountService accountService;

	public JWTAuthenticationProvider(JWTService jwtService, AccountService accountService) {
		this.jwtService = jwtService;
		this.accountService = accountService;
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String token = ((JWTTokenAuthentication) authentication).getToken();
		try {
			DecodedJWT decodedJWT = jwtService.validateToken(token);
			Account account = accountService.findAccount(decodedJWT.getSubject());
			return new UsernamePasswordAuthenticationToken(account, null, Collections.emptyList());
		} catch (Exception ex) {
			log.error("validate token failed, token: {}", token, ex);
			throw new BadCredentialsException("token is invalid!");
		}

	}

	@Override
	public boolean supports(Class<?> aClass) {
		return JWTTokenAuthentication.class.isAssignableFrom(aClass);
	}
}