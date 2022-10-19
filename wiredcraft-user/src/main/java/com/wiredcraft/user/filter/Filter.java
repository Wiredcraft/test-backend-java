package com.wiredcraft.user.filter;

import com.wiredcraft.user.utils.RequestHeaderUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author jeremy.zhang
 * @date 2022-10-19
 */
@WebFilter(filterName = "headerFilter", urlPatterns = "/*")
@Component
public class Filter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String uuid = httpServletRequest.getHeader("uuid");
        if (StringUtils.isNotEmpty(uuid)) {
            RequestHeaderUtils.set(uuid);
        } else {
            RequestHeaderUtils.set("-1");
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    @Override
    public void destroy() {
        RequestHeaderUtils.clear();
    }
}
