package org.fede.pruebas.controllers;

import org.fede.pruebas.entities.Empleados;
import org.fede.pruebas.repositories.EmpleadoInterface;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class EmpleadoController {

    private final EmpleadoInterface repository;

    public EmpleadoController(EmpleadoInterface repository) {
        this.repository = repository;
    }

    @GetMapping("/empleados")
    public List<Empleados> empleados() {
        return repository.findAll();
    }
}
