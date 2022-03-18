package com.zhangyongxin.demo.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.zhangyongxin.demo.common.Result;
import com.zhangyongxin.demo.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.auth.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.BeanNameViewResolver;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * 异常跨域等统一处理
 *
 * @Auther zhangyongxin
 * @date 2022/3/17 下午12:03
 */
@Configuration
@Slf4j
public class WebMvcConfig implements WebMvcConfigurer {
    @Resource
    private UserLoginInterceptor userLoginInterceptor;

    //跨域支持
    @Bean
    public FilterRegistrationBean corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return bean;
    }

    @Override
    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> resolvers) {
        resolvers.add((httpServletRequest, httpServletResponse, handler, exception) -> {
            Result result = new Result();
            if (exception instanceof AccessDeniedException || exception instanceof AuthenticationException) {
                result = new Result(HttpServletResponse.SC_FORBIDDEN, exception.getMessage());
            } else if (exception instanceof ServiceException) {
                result = new Result(HttpServletResponse.SC_BAD_REQUEST, exception.getMessage());
            } else {
                result.setCode(HttpServletResponse.SC_INTERNAL_SERVER_ERROR).setMessage("接口 [" + httpServletRequest.getRequestURI() + "] 内部错误，请联系管理员");
                String message;
                if (handler instanceof HandlerMethod) {
                    HandlerMethod handlerMethod = (HandlerMethod) handler;
                    message = String.format("接口 [%s] 出现异常，方法：%s.%s，异常摘要：%s", httpServletRequest.getRequestURI(), handlerMethod.getBean().getClass().getName(), handlerMethod.getMethod().getName(), exception.getMessage());
                } else {
                    message = exception.getMessage();
                }
                log.error(message, exception);
            }
            try {
                httpServletResponse.getWriter().write(new Gson().toJson(result));
                httpServletResponse.setCharacterEncoding("UTF-8");
                httpServletResponse.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON.toString());
                httpServletResponse.setStatus(HttpServletResponse.SC_OK);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return new ModelAndView();
        });
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        if (null != userLoginInterceptor) {
            InterceptorRegistration interceptorRegistration = registry.addInterceptor(userLoginInterceptor);
            interceptorRegistration.excludePathPatterns(userLoginInterceptor.getExcludePath());
            interceptorRegistration.excludePathPatterns("/webjars/**", "/swagger-ui.html", "/error", "/v2/**", "/swagger*/**", "/**/*.html");
        }
    }

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.favorPathExtension(true).useRegisteredExtensionsOnly(true).
                favorParameter(true).parameterName("format").
                ignoreAcceptHeader(true)
                .defaultContentType(MediaType.APPLICATION_JSON)
                .mediaType("json", MediaType.APPLICATION_JSON);
    }


    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(createFastJsonHttpMessageConverter());
    }

    private FastJsonHttpMessageConverter createFastJsonHttpMessageConverter() {
        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
        FastJsonConfig config = new FastJsonConfig();
        config.setSerializerFeatures(SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullStringAsEmpty, SerializerFeature.DisableCircularReferenceDetect, SerializerFeature.WriteNullNumberAsZero);
        converter.setFastJsonConfig(config);
        converter.setDefaultCharset(StandardCharsets.UTF_8);
        return converter;
    }

    @Bean
    public ViewResolver contentNegotiatingViewResolver(ContentNegotiationManager manager) {
        // Define the view resolvers
        ViewResolver beanNameViewResolver = new BeanNameViewResolver();
        List<ViewResolver> resolvers = Lists.newArrayList(beanNameViewResolver);
        ContentNegotiatingViewResolver resolver = new ContentNegotiatingViewResolver();
        resolver.setViewResolvers(resolvers);
        resolver.setContentNegotiationManager(manager);
        return resolver;
    }
}
