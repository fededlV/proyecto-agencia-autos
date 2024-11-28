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
                                     @Value("${url.microservicio.pruebas}") String uriPruebas,
                                     @Value("${url.microservicio.notificaciones}") String uriNotificaciones,
                                     @Value("${url.microservicio.reportes}") String uriReportes,
                                     @Value("${url.microservicio.posiciones}") String uriPosiciones
    ) {
        return builder.routes()
                .route(p -> p.path("/api/pruebas/**").uri(uriPruebas))
                .route(p -> p.path("/api/vehiculos/**").uri(uriNotificaciones))
                .route(p -> p.path("/api/reportes/**").uri(uriReportes))
                .route(p -> p.path("/api/posiciones/**").uri(uriPosiciones))
                .build();
    }
}
