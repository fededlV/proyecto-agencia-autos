package org.fede.pruebas.controllers;

import jakarta.validation.Valid;
import org.fede.pruebas.dto.PruebaDto;
import org.fede.pruebas.dto.PruebaResponseDto;
import org.fede.pruebas.entities.Prueba;
import org.fede.pruebas.services.PruebasServices;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PruebaController {

    private final PruebasServices services;

    public PruebaController(PruebasServices services) {
        this.services = services;
    }

    @GetMapping("/pruebas")
    public List<PruebaResponseDto> findAll() {
        return this.services.findAll();
    }

    @PostMapping("/pruebaCreacion")
    public PruebaResponseDto createPrueba(
            @Valid @RequestBody PruebaDto pruebaDto
            ) {
        return this.services.create(pruebaDto);
    }
}
