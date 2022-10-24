package com.craig.user.filter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

import com.craig.user.filter.wrap.CachedBodyHttpServletRequest;
import com.craig.user.filter.wrap.CachedBodyHttpServletResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class LogFilter extends OncePerRequestFilter implements Ordered {

    private static List<String> notAllowedMethod;

    private static List<String> notAllowedUri;

    static {
        notAllowedMethod = List.of("OPTIONS", "HEAD");

        notAllowedUri = List.of("/v3/api-docs", "/swagger-ui/index.html", "/favicon.ico");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        if (!needLog(request)) {
            filterChain.doFilter(request, response);
            return;
        }
        if (!(request instanceof CachedBodyHttpServletRequest)) {
            request = new CachedBodyHttpServletRequest(request);
        }
        if (!(response instanceof CachedBodyHttpServletResponse)) {
            response = new CachedBodyHttpServletResponse(response);
        }
        long startTime = System.currentTimeMillis();
        String path = request.getRequestURI();
        try {
            logRequest(request);
            filterChain.doFilter(request, response);
        } finally {
            long duration = System.currentTimeMillis() - startTime;
            logResponse(response, path, request.getMethod(), duration);
        }

    }

    private boolean needLog(HttpServletRequest request) {
        Enumeration<String> headerNames = request.getHeaderNames();
        if (headerNames != null) {
            while (headerNames.hasMoreElements()) {
                String headerName = headerNames.nextElement();
                String headerValue = request.getHeader(headerName);
                if (headerValue != null && headerValue.contains("multipart/form-data")) {
                    return false;
                }
            }
        }

        if (notAllowedMethod.contains(request.getMethod().toUpperCase())) {
            return false;
        }
        return !notAllowedUri.contains(request.getRequestURI());
    }

    private void logRequest(HttpServletRequest request) {
        StringBuilder logText = new StringBuilder();
        logText.append(request.getMethod() + " " + request.getRequestURI() + "\n");

        if (StringUtils.isNotEmpty(request.getQueryString())) {
            logText.append("QUERY STRING:" + request.getQueryString() + "\n");
        }

        Enumeration<String> headerNames = request.getHeaderNames();
        if (headerNames != null) {
            while (headerNames.hasMoreElements()) {
                String headerName = headerNames.nextElement();
                logText.append(headerName + ":" + request.getHeader(headerName) + "\n");
            }
        }
        if (StringUtils.isNotEmpty(request.getContentType())
                && request.getContentType().toLowerCase().contains("application/json")) {
            logText.append(getRequestBody(request) + "\n");
        }
        log.info(logText.toString());
    }

    private void logResponse(HttpServletResponse response, String url, String method, long duration) {
        StringBuilder logText = new StringBuilder();
        logText.append("SEND RESPONSE:" + url + "\n");
        logText.append("METHOD:" + method + "\n");
        logText.append("HTTP STATUS:" + response.getStatus() + "\n");
        logText.append("DURATION:" + duration + "ms" + "\n");

        Collection<String> headerNames = response.getHeaderNames();
        headerNames.forEach(h -> {
            logText.append(h + ":" + response.getHeader(h) + "\n");
        });

        if (StringUtils.isNotEmpty(response.getContentType())
                && response.getContentType().toLowerCase().contains("application/json")) {
            logText.append(getResponseBody(response) + "\n");
        }

        log.info(logText.toString());
    }

    private String getRequestBody(HttpServletRequest request) {
        String requestBody = "";
        CachedBodyHttpServletRequest wrapper = WebUtils.getNativeRequest(request, CachedBodyHttpServletRequest.class);
        if (wrapper != null) {
            requestBody = IOUtils.toString(wrapper.getCachedBody(), StandardCharsets.UTF_8.name());

        }
        return requestBody;
    }

    private String getResponseBody(HttpServletResponse response) {
        String responseBody = "";
        CachedBodyHttpServletResponse wrapper = WebUtils.getNativeResponse(response,
                CachedBodyHttpServletResponse.class);
        if (wrapper != null) {
            try {
                responseBody = IOUtils.toString(wrapper.getContent().getInputStream(), wrapper.getCharacterEncoding());
            } catch (IOException e) {
                // NOOP
            }
        }
        return responseBody;
    }

    @Override
    public int getOrder() {
        // make sure the filter can be applied as first one before other filters
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
