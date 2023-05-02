package br.com.delazeri.library.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Library")
                        .version("v1")
                        .description("Library API developed using Java and Spring Boot")
                        .termsOfService("https://github.com/Gabriel-Delazeri")
                        .license(
                                new License().
                                        name("Apache 2.0")
                                        .url("https://github.com/Gabriel-Delazeri")
                        )
                );
    }
}
