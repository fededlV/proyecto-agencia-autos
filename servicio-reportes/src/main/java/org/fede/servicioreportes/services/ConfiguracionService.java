package org.fede.servicioreportes.services;

import org.fede.servicioreportes.dto.ConfiguracionDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class ConfiguracionService {

    private final WebClient webClient;
    private final String configuracionUrl;

    // Inyección de la URL desde el archivo de propiedades
    public ConfiguracionService(WebClient webClient, @Value("${external.api.configuracion-url}") String configuracionUrl) {
        this.webClient = webClient;
        this.configuracionUrl = configuracionUrl;
    }

    public ConfiguracionDto obtenerConfiguracion() {
        return webClient.get()
                .uri(configuracionUrl)
                .retrieve()
                .bodyToMono(ConfiguracionDto.class)
                .block();
    }
}