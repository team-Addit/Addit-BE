package com.pozzle.addit.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        Info info = new Info()
            .version("1.0")
            .title("Addit")
            .description("Addit API");

        Server localServer = new Server();
        localServer.setUrl("http://localhost:8080");

        Server prodServer = new Server();
        localServer.setUrl("https://port-0-addit-be-m64o37zm61224fba.sel4.cloudtype.app");

        return new OpenAPI()
            .info(info)
            .servers(List.of(localServer));
    }
}
