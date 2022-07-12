package com.lyt.backend.configurations;

import com.fasterxml.classmate.TypeResolver;
import com.lyt.backend.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.async.DeferredResult;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseBuilder;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.schema.Example;
import springfox.documentation.schema.WildcardType;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static java.util.Collections.singletonList;
import static springfox.documentation.schema.AlternateTypeRules.newRule;

/**
 * this configuration is copied from <a href="http://springfox.github.io/springfox/docs/current/">...</a>
 */
@Configuration
@Import({springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration.class})
@EnableOpenApi
public class SwaggerConfig {
    @Bean
    public Docket petApi() {
        return new Docket(DocumentationType.OAS_30)
                .enable(true)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.lyt.backend"))
                .build()
                .apiInfo(apiInfo())
                .pathMapping("/")
                .directModelSubstitute(LocalDate.class, String.class)
                .genericModelSubstitutes(ResponseEntity.class)
                .alternateTypeRules(
                        newRule(typeResolver.resolve(DeferredResult.class,
                                        typeResolver.resolve(ResponseEntity.class, WildcardType.class)),
                                typeResolver.resolve(WildcardType.class)))
                .useDefaultResponseMessages(false)
                .globalResponses(HttpMethod.GET,
                        Arrays.asList(new ResponseBuilder()
                                                .code("500")
                                                .description("When there's an exception thrown in application")
                                                .examples(singletonList(new Example("","","", """
                                                        {
                                                          "data": null,
                                                           "message": "An internal error happened on your request",
                                                          "succeed": false
                                                        }""","", "application/json")))
                                                .representation(MediaType.APPLICATION_JSON)
                                                .apply(r ->
                                                        r.model(m ->
                                                                m.referenceModel(ref ->
                                                                        ref.key(k ->
                                                                                k.qualifiedModelName(q ->
                                                                                        q.namespace("some:namespace")
                                                                                                .name("ERROR"))))))
                                                .build(),
                                        new ResponseBuilder().
                                                code("404").
                                                description("The user requested is not found").build()
                      ))
                .globalResponses(HttpMethod.POST,
                        singletonList(new ResponseBuilder()
                                .code("500")
                                .description("When there's an exception thrown in application")
                                .examples(singletonList(new Example("","","", """
                                        {
                                          "data": null,
                                           "message": "An internal error happened on your request",
                                          "succeed": false
                                        }""","", "application/json")))
                                .representation(MediaType.APPLICATION_JSON)
                                .apply(r ->
                                        r.model(m ->
                                                m.referenceModel(ref ->
                                                        ref.key(k ->
                                                                k.qualifiedModelName(q ->
                                                                        q.namespace("some:namespace")
                                                                                .name("ERROR"))))))
                                .build()))
                .globalResponses(HttpMethod.DELETE,
                        Arrays.asList(new ResponseBuilder()
                                        .code("500")
                                        .description("When there's an exception thrown in application")
                                        .examples(singletonList(new Example("","","", """
                                                {
                                                  "data": null,
                                                   "message": "An internal error happened on your request",
                                                  "succeed": false
                                                }""","", "application/json")))
                                        .representation(MediaType.APPLICATION_JSON)
                                        .apply(r ->
                                                r.model(m ->
                                                        m.referenceModel(ref ->
                                                                ref.key(k ->
                                                                        k.qualifiedModelName(q ->
                                                                                q.namespace("some:namespace")
                                                                                        .name("ERROR"))))))
                                        .build(),
                                new ResponseBuilder().
                                        code("404").
                                        description("The user requested is not found").build()
                        ))
                .securitySchemes(singletonList(apiKey()))
                .securityContexts(singletonList(securityContext()))
                .enableUrlTemplating(true)
                /*
                .globalRequestParameters(
                        singletonList(new springfox.documentation.builders.RequestParameterBuilder()
                                .name("someGlobalParameter")
                                .description("Description of someGlobalParameter")
                                .in(ParameterType.QUERY)
                                .required(true)
                                .query(q -> q.model(m -> m.scalarModel(ScalarType.STRING)))
                                .build()))
                 */
                .additionalModels(typeResolver.resolve(User.class));
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("Test-Backend api document")
                .description("Apis of CRUD of users")
                .version("1.0")
                .license("Apache LICENSE 2.0")
                .build();
    }

    @Autowired
    private TypeResolver typeResolver;

    private ApiKey apiKey() {
        return new ApiKey("mykey", "api_key", "header");
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.regex("/anyPath.*"))
                .build();
    }

    List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope
                = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return singletonList(
                new SecurityReference("mykey", authorizationScopes));
    }

    @Bean
    SecurityConfiguration security() {
        return SecurityConfigurationBuilder.builder()
                .clientId("test-app-client-id")
                .clientSecret("test-app-client-secret")
                .realm("test-app-realm")
                .appName("test-app")
                .scopeSeparator(",")
                .additionalQueryStringParams(null)
                .useBasicAuthenticationWithAccessCodeGrant(false)
                .enableCsrfSupport(false)
                .build();
    }

    @Bean
    UiConfiguration uiConfig() {
        return UiConfigurationBuilder.builder()
                .deepLinking(true)
                .displayOperationId(false)
                .defaultModelsExpandDepth(1)
                .defaultModelExpandDepth(1)
                .defaultModelRendering(ModelRendering.EXAMPLE)
                .displayRequestDuration(false)
                .docExpansion(DocExpansion.NONE)
                .filter(false)
                .maxDisplayedTags(null)
                .operationsSorter(OperationsSorter.ALPHA)
                .showExtensions(false)
                .showCommonExtensions(false)
                .tagsSorter(TagsSorter.ALPHA)
                .supportedSubmitMethods(UiConfiguration.Constants.DEFAULT_SUBMIT_METHODS)
                .validatorUrl(null)
                .build();
    }
}
