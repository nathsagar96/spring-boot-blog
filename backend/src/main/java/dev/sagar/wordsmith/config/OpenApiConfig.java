package dev.sagar.wordsmith.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@SecurityScheme(
        name = "Bearer Authentication",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
public class OpenApiConfig {

    @Value("${api.version}")
    private String version;

    @Value("${api.description}")
    private String description;

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("WordSmith API")
                        .version(version)
                        .contact(new Contact()
                                .name("Sagar Nath")
                                .email("nathsagar96@gmail.com"))
                        .description(description)
                        .license(new License()
                                .name("Apache 2.0").url("https://www.apache.org/licenses/LICENSE-2.0")));
    }
}
