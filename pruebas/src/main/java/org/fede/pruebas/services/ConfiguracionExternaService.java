package org.fede.pruebas.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.fede.pruebas.dto.ConfiguracionExterna;

@Service
public class ConfiguracionExternaService {

    @Value("${external.api.configuracion-url}")
    private String url;

    public ConfiguracionExterna obtenerConfiguracion() {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(url, ConfiguracionExterna.class);
    }
}
