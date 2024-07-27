package com.Demo.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;


@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI api() {
        return new OpenAPI()
                .info(new Info()
                        .title("Demo Application")
                        .description("API for Demo application")
                        .version("1.0.0"))
                .servers(Collections.singletonList(new Server().url("http://localhost:8080")));  // Optional: Set server details
    }
}


