package org.fede.pruebas.controllers;

import org.fede.pruebas.dto.EmpleadoDto;
import org.fede.pruebas.entities.Empleado;
import org.fede.pruebas.services.EmpleadoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class EmpleadoController {

    private final EmpleadoService empleadoService;

    public EmpleadoController(EmpleadoService empleadoService) {
        this.empleadoService = empleadoService;
    }

    @GetMapping("/empleados")
    public List<EmpleadoDto> getAll() {
        return empleadoService.findAll();
    }
}
