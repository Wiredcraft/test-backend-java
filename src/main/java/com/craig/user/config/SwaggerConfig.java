package com.craig.user.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class SwaggerConfig {
  @Bean
  public OpenAPI openAPI() {
    return new OpenAPI()
        .info(new Info().title("User-service APIs")
            .description("A user-service for wiredcraft interview test")
            .version("v0.0.1")
            .license(new License().name("Apache 2.0").url("http://springdoc.org")));
  }
}
