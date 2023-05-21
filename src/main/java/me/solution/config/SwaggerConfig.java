package me.solution.config;

import com.google.common.base.Predicate;
import io.swagger.annotations.ApiOperation;
import me.solution.annotations.NonToken;
import me.solution.constants.AuthConstant;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Predicates.and;
import static com.google.common.base.Predicates.not;
import static springfox.documentation.builders.RequestHandlerSelectors.basePackage;
import static springfox.documentation.builders.RequestHandlerSelectors.withMethodAnnotation;

/**
 * swagger api config
 *
 * @author davincix
 * @since 2023/5/22 03:21
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @SuppressWarnings("Guava")
    @Bean
    public Docket tokenApi() throws IOException {
        Predicate<RequestHandler> tokenSelector = and(
                basePackage("me.solution.endpoint"),
                withMethodAnnotation(ApiOperation.class),
                not(withMethodAnnotation(Deprecated.class)),
                not(withMethodAnnotation(NonToken.class)));
        List<Parameter> parameters = new ArrayList<>();
        parameters.add(new ParameterBuilder()
                .name(AuthConstant.TOKEN_HEADER)
                .description("token")
                .modelRef(new ModelRef("string"))
                .parameterType("header")
                .required(true).build());

        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("token api")
                .select()
                .apis(tokenSelector)
                .build()
                .forCodeGeneration(true)
                .genericModelSubstitutes(ResponseEntity.class)
                .globalOperationParameters(parameters)
                .ignoredParameterTypes(HttpServletResponse.class, HttpServletRequest.class);
    }

    @SuppressWarnings("Guava")
    @Bean
    public Docket nonTokenApi() throws IOException {
        Predicate<RequestHandler> noTokenSelector = and(
                basePackage("me.solution.endpoint"),
                withMethodAnnotation(ApiOperation.class),
                not(withMethodAnnotation(Deprecated.class)),
                withMethodAnnotation(NonToken.class));
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("non-token api")
                .select()
                .apis(noTokenSelector)
                .build()
                .forCodeGeneration(true)
                .genericModelSubstitutes(ResponseEntity.class)
                .ignoredParameterTypes(HttpServletResponse.class, HttpServletRequest.class);
    }
}
