package com.example.ch16.config;

import static org.springframework.security.config.Elements.JWT;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityScheme.In;
import io.swagger.v3.oas.models.security.SecurityScheme.Type;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {

    Components components = new Components()
            .addSecuritySchemes(JWT, getJwtSecurityScheme());
    SecurityRequirement securityItem = new SecurityRequirement()
            .addList(JWT);
    Info info = new Info()
            .version("v1.0.0")
            .title("API 타이틀")
            .description("API Description");

        return new OpenAPI().components(components).addSecurityItem(securityItem)
                .info(info);
}

    private SecurityScheme getJwtSecurityScheme() {
        return new SecurityScheme().type(Type.APIKEY).in(In.HEADER).name("X-AUTH-TOKEN");
    }

}