package org.fede.apigateway.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GWConfig {

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder,
                                     @Value("${url.microservicio.pruebas}") String uriPruebas) {
        return builder.routes()
                .route(p -> p.path("/api/empleados/**").uri(uriPruebas))
                .build();
    }
}
