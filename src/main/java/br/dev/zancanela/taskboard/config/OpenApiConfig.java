package br.dev.zancanela.taskboard.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(servers = {@Server(url = "/", description = "Localhost")})
public class OpenApiConfig {

    public static final String API_TITLE = "Taskboard API";
    public static final String API_DESCRIPTION = "Api para controle de tarefas em um quadro Kanban.";
    public static final String API_VERSION = "0.0.1-SNAPSHOT";
    public static final String API_LICENSE = "Apache License";
    public static final String API_LICENSE_URL = "https://www.apache.org/licenses/LICENSE-2.0";
    public static final String CONTACT_NAME = "Luis Zancanela";
    public static final String CONTACT_URL = "https://zancanela.dev.br";

    @Bean
    OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title(API_TITLE)
                        .version(API_VERSION)
                        .description(API_DESCRIPTION)
                        .termsOfService("http://swagger.io/terms/")
                        .license(new License().
                                name(API_LICENSE).
                                url(API_LICENSE_URL))
                        .contact(new Contact()
                                .name(CONTACT_NAME)
                                .url(CONTACT_URL)));
    }
}
