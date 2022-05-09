package io.github.jerrychin.testbackendjava.core.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.jerrychin.testbackendjava.model.vo.ResponseVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

@Component
public class JWTAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

	public static final String APPLICATION_JSON = "application/json";

	public JWTAuthenticationFilter(JWTAuthenticationProvider authenticationProvider, ObjectMapper objectMapper) {

		// intercept all request by default.
		super(new AntPathRequestMatcher("/**"),
				new ProviderManager(Collections.singletonList(authenticationProvider)));

		// default handler for success authentication is redirecting request, so we need to override it.
		this.setAuthenticationSuccessHandler((request, response, authentication) -> {
			// no-op
		});

		// return friendly error message if authentication process is failed.
		this.setAuthenticationFailureHandler((request, response, exception) -> {
			response.addHeader(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON);
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
			objectMapper.writeValue(response.getWriter(), new ResponseVO(exception.getMessage()));
		});
	}

	@Override
	protected boolean requiresAuthentication(HttpServletRequest request, HttpServletResponse response) {

		// we are only interested in requests with AUTHORIZATION header.
		return StringUtils.isNotBlank(request.getHeader(HttpHeaders.AUTHORIZATION));
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
											Authentication authResult) throws IOException, ServletException {
		super.successfulAuthentication(request, response, chain, authResult);

		// This line is very important! we want to continue filter chaining after authentication.
		chain.doFilter(request, response);
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		String bearToken = request.getHeader(HttpHeaders.AUTHORIZATION);
		String jwtToken = extractJwtToken(bearToken);
		return getAuthenticationManager().authenticate(new JWTTokenAuthentication(jwtToken));
	}

	/**
	 *
	 * the token is in the form "Bearer token, we need to remove Bearer word and get only the token.
	 *
	 * @param bearToken the token from authorization header.
	 * @return token.
	 */
	private String extractJwtToken(String bearToken) {
		if (StringUtils.isBlank(bearToken)) {
			throw new BadCredentialsException("JWT Token does not begin with Bearer String");
		}

		return bearToken.substring("Bearer ".length());
	}

}