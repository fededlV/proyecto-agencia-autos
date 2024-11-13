package org.fede.servicioreportes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
@ImportAutoConfiguration(exclude = WebMvcAutoConfiguration.class)
public class ServicioReportesApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServicioReportesApplication.class, args);
    }

}
