package com.pozzle.addit.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI(HttpServletRequest request) {
        String scheme = request.getScheme(); // http or https
        String host = request.getServerName(); // localhost or domain name
        int port = request.getServerPort();

        // 포트를 추가해야 하는지 확인
        String baseUrl = (port == 80 || port == 443)
            ? String.format("%s://%s", scheme, host)
            : String.format("%s://%s:%d", scheme, host, port);

        Server server = new Server().url(baseUrl);

        Info info = new Info()
            .version("1.0")
            .title("Addit")
            .description("Addit API");

        return new OpenAPI()
            .info(info)
            .servers(List.of(server));
    }
}
