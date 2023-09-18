package com.springboot.blog.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI usersMicroserviceOpenApi() {
        return new OpenAPI()
                .info(new Info().title("Blog API")
                        .description("Rest APIs for Blog Application")
                        .version("1.0")
                );
    }
}
