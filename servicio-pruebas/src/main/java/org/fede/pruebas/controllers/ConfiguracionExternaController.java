package org.fede.pruebas.controllers;

import org.fede.pruebas.dto.ConfiguracionExterna;
import org.fede.pruebas.services.ConfiguracionExternaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/configuracion")
public class ConfiguracionExternaController {

    private final ConfiguracionExternaService configuracionExternaService;

    @Autowired
    public ConfiguracionExternaController(ConfiguracionExternaService configuracionExternaService) {
        this.configuracionExternaService = configuracionExternaService;
    }

    @GetMapping("/externa")
    public ConfiguracionExterna obtenerConfiguracionExterna() {
        return configuracionExternaService.obtenerConfiguracion();
    }
}
