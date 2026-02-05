package com.pragma.ms_personas.infrastructure.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Personas API",
                version = "1.0",
                description = "API de gestión de personas"
        )
)
public class OpenApiConfig {
}