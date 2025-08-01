package com.backend.portal.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {
    @Bean
    public OpenAPI usersOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("User Management API")
                        .description(
                                "Features:\n" +
                                        "- Fetches user data from an external API (https://dummyjson.com/users)\n" +
                                        "- Populates the in-memory H2 database with the fetched users\n" +
                                        "- Supports searching users by:\n" +
                                        "  • Email (ext match)\n" +
                                        "  • ID (ext match)\n" +
                                        "  • First Name, Last Name, and SSN using Hibernate Search (full-text search as well as fizzy search with 1 char)\n" +
                                        "- Uses Hibernate Search with Lucene for free-text search\n" +
                                        "- REST API built with Spring Boot"
                        )
                        .version("v1.0")
                        .contact(new Contact()
                                .name("Shusovan Sarkar")
                                .email("shusovanofficial@gmail.com")
                        )
                );
    }
}
