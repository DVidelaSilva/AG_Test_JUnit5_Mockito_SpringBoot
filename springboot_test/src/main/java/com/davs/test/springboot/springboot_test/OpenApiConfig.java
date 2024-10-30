package com.davs.test.springboot.springboot_test;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Ejemplo")
                        .version("1.0")
                        .description("Documentación de la API de ejemplo con Springdoc OpenAPI y Swagger UI"));
    }

}
