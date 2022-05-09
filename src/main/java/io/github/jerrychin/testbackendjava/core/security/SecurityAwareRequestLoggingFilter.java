package io.github.jerrychin.testbackendjava.core.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.AbstractRequestLoggingFilter;

import javax.servlet.http.HttpServletRequest;

/**
 * Request logging with SLF4J.
 */
public class SecurityAwareRequestLoggingFilter extends AbstractRequestLoggingFilter {
    private static final Logger log = LoggerFactory.getLogger(SecurityAwareRequestLoggingFilter.class);

    public SecurityAwareRequestLoggingFilter() {
    }

    protected boolean shouldLog(HttpServletRequest request) {
        return log.isDebugEnabled();
    }

    protected void beforeRequest(HttpServletRequest request, String message) {
        log.debug(message);
    }

    protected void afterRequest(HttpServletRequest request, String message) {
        log.debug(message);
    }
}
