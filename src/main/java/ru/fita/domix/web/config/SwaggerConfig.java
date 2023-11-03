package ru.fita.domix.web.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import jakarta.servlet.ServletContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI(ServletContext servletContext) {
        return new OpenAPI()
                .info(new Info().title("API серверной части сайта Domix"))
                .servers(
                        List.of(new Server().url("https://domix.fita.cc")
                                        .description("Prod service"),
                                new Server().url("http://localhost:8090")
                                        .description("Dev service"),
                                new Server().url("http://localhost:8080")
                                        .description("Local service")));
    }
}