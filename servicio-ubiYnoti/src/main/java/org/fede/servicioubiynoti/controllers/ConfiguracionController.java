package org.fede.servicioubiynoti.controllers;

import org.fede.servicioreportes.dto.ConfiguracionDto;
import org.fede.servicioreportes.services.ConfiguracionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConfiguracionController {

    @Autowired
    private ConfiguracionService configuracionService;

    @GetMapping("/api/configuracion")
    public ConfiguracionDto obtenerConfiguracion() {
        return configuracionService.obtenerConfiguracion();
    }
}
