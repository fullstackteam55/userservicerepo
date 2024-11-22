package com.resturantservice;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("UserService API")
                        .description("API documentation for the UserService Management. The system supports functionality of user management, scalability, maintainability and reliability")
                        .version("v1.0")
                        .license(new License().name("Version v1").url("http://localhost:9091/userservices/v3/api-docs")))
                .externalDocs(new ExternalDocumentation()
                        .description("User Service API Documentation")
                        .url("http://localhost:9091/docs"));
    }
    
//    @Bean
//    public OpenAPI secureOpenAPI() {
//        return new OpenAPI()
//                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
//                .components(new io.swagger.v3.oas.models.Components()
//                        .addSecuritySchemes("bearerAuth",
//                                new SecurityScheme()
//                                        .type(SecurityScheme.Type.HTTP)
//                                        .scheme("bearer")
//                                        .bearerFormat("JWT")));
//    }
}

