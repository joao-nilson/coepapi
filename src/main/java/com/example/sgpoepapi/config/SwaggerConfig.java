package com.example.sgpoepapi.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    private static final String SECURITY_SCHEME = "JWT";

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(apiInfo())
                .components(new Components()
                        .addSecuritySchemes(SECURITY_SCHEME, apiKey()))
                .addSecurityItem(new SecurityRequirement().addList(SECURITY_SCHEME));
    }

    private Info apiInfo() {
        return new Info()
                .title("SGPOE API")
                .description("API do Sistema de Gestão de Produção de Oócitos e Embriões")
                .version("1.0")
                .contact(contact());
    }

    private Contact contact() {
        return new Contact()
                .name("Gabhriel Velasco");
    }

    private SecurityScheme apiKey() {
        return new SecurityScheme()
                .name("Authorization")
                .type(SecurityScheme.Type.APIKEY)
                .in(SecurityScheme.In.HEADER);
    }
}
