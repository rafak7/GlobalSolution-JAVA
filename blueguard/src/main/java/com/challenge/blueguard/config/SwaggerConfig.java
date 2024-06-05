package com.challenge.blueguard.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("BlueGuard API")
                        .description("API para gerenciamento de observações e monitoramento marítimo.")
                        .version("v1.0")
                        .contact(new Contact()
                                .name("BlueGuard Support")
                                .email("support@blueguard.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://springdoc.org")));
    }

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("public")
                .pathsToMatch("/**")  // Inclui todos os endpoints
                .build();
    }

    @Bean
    public ExternalDocumentation externalDocumentation() {
        return new ExternalDocumentation()
                .description("BlueGuard Wiki Documentation")
                .url("https://blueguard.wiki.github.org/docs");
    }
}