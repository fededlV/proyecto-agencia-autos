package org.fede.servicioubiynoti.services;

import org.fede.servicioreportes.dto.ConfiguracionDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;

@Service
public class ConfiguracionService {

    private final RestTemplate restTemplate;
    private final String configuracionUrl;

    // Inyección de la URL desde el archivo de propiedades
    public ConfiguracionService(RestTemplate restTemplate, @Value("${external.api.configuracion-url}") String configuracionUrl) {
        this.restTemplate = restTemplate;
        this.configuracionUrl = configuracionUrl;
    }

    public ConfiguracionDto obtenerConfiguracion() {
        // Realizando la solicitud GET con RestTemplate
        ResponseEntity<ConfiguracionDto> response = restTemplate.getForEntity(configuracionUrl, ConfiguracionDto.class);

        // Retornamos el cuerpo de la respuesta (ConfiguracionDto)
        return response.getBody();
    }
}
