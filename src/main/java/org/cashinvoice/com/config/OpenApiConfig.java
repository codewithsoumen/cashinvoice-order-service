package org.cashinvoice.com.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Cash Invoice Order API")
                        .version("v1")
                        .description("API documentation for order endpoints")
                        .contact(new Contact().name("Cash Invoice").email("dev@cashinvoice.org"))
                );
    }
}

