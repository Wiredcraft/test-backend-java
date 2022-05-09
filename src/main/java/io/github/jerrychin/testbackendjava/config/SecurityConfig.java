package io.github.jerrychin.testbackendjava.config;

import io.github.jerrychin.testbackendjava.core.security.SecurityAwareRequestLoggingFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import io.github.jerrychin.testbackendjava.core.security.JWTAuthenticationFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Security related configuration.
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	/**
	 * Handle JWT authentication.
	 */
	@Autowired
	private JWTAuthenticationFilter jwtAuthenticationFilter;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
				.authorizeRequests()

				// permit static resources for all
				.antMatchers("/static/**", "/index.html", "/profile.html",
						"/signin.html", "/signup.html").permitAll()

				// permit swagger access for all
				.antMatchers("/swagger-ui/**", "/swagger-resources/**", "/v3/api-docs").permitAll()

				// permit auth related access for all
				.antMatchers("/api/v1/auth/**", "/error").permitAll()

				// permit access to some people apis
				.antMatchers("/api/v1/people", "/api/v1/people/{id}/profile").permitAll()

				// all other requests are required to be authenticated.
				.anyRequest().authenticated()
				.and()

				// we're using JWT token, so no session is required.
				.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()

				// form login is not needed in our case.
				.formLogin().disable()
				.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
	}

	/**
	 * Register our request logging filter.
	 */
	@Bean
	public SecurityAwareRequestLoggingFilter requestLoggingFilter() {
		SecurityAwareRequestLoggingFilter loggingFilter = new SecurityAwareRequestLoggingFilter();
		loggingFilter.setIncludeClientInfo(true);
		loggingFilter.setIncludeQueryString(true);

		// logging payload as well
		loggingFilter.setIncludePayload(true);
		loggingFilter.setMaxPayloadLength(64000);
		return loggingFilter;
	}
}
