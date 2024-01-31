package com.example.hibernate.reactive.demo.config;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;

import java.util.Properties;

/**
 * The {@code SwaggerConfig}
 * class is responsible for configuring Swagger/OpenAPI documentation for the application.
 */
@Configuration
@SuppressWarnings("unused")
public class SwaggerConfiguration {
    @ConditionalOnMissingBean
    @Bean
    public BuildProperties buildProperties() {
        return new BuildProperties(new Properties());
    }

    /**
     * Configures and returns a custom OpenAPI instance for Swagger documentation.
     *
     * @param buildProperties   The build properties of the application.
     * @param applicationConfig Properties for application.
     * @return A custom OpenAPI instance.
     */
    @Bean
    public OpenAPI customOpenAPIConfig(
            final BuildProperties buildProperties,
            final Environment applicationConfig,
            final ServerProperties serverProperties) {
        OpenAPI openAPI = new OpenAPI().info(new Info().version(buildProperties.getVersion())
                .title("Hibernate reactive demo project")
                .description("This Swagger Interface details out the APIs for Hibernate reactive demo project")
                .contact(new Contact().name("Vishwajeet").url("https://www.github.com/Svishwajeet04")));
        SecurityScheme accessKey = new SecurityScheme().name(HttpHeaders.AUTHORIZATION)
                .type(SecurityScheme.Type.APIKEY)
                .bearerFormat("JWT")
                .in(SecurityScheme.In.HEADER);
        openAPI.addSecurityItem(new SecurityRequirement().addList(HttpHeaders.AUTHORIZATION))
                .schemaRequirement(HttpHeaders.AUTHORIZATION, accessKey);
        return openAPI;
    }
}

