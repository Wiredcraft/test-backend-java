package com.coffee.user.annotation;

import org.springframework.http.HttpMethod;

import java.lang.annotation.*;

/**

 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RestApi {

    HttpMethod method() default HttpMethod.GET;

    String description() default  "";
}
