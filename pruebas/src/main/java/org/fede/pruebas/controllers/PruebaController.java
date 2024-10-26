package org.fede.pruebas.controllers;

import org.fede.pruebas.entities.Pruebas;
import org.fede.pruebas.services.PruebasServices;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PruebaController {

    private final PruebasServices services;

    public PruebaController(PruebasServices services) {
        this.services = services;
    }

    @GetMapping("/pruebas")
    public List<Pruebas> findAll() {
        return this.services.findAll();
    }
}
