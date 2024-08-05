package com.hungnt.notify_service.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI(){
        return new OpenAPI()
                // Thông tin chi tiết cho API
                .info(new Info()
                        .title("API Notify Service Document")
                        .version("v1.0")
                        .description("API Service")
                )
                // Server có thể truy cập
                .servers(List.of(
                        new Server().url("http://localhost:8889").description("Local Server")
                ));
    }

    @Bean
    public GroupedOpenApi groupedOpenApi(){
        return GroupedOpenApi.builder()
                .group("API Group 2")
                .packagesToScan("com.hungnt.notify_service.controller")
                .build();
    }
}
