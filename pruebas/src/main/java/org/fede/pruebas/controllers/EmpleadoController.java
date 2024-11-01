package org.fede.pruebas.controllers;

import jakarta.validation.Valid;
import org.fede.pruebas.dto.EmpleadoDto;
import org.fede.pruebas.entities.Empleado;
import org.fede.pruebas.services.EmpleadoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/empleados")
public class EmpleadoController {

    private final EmpleadoService empleadoService;

    public EmpleadoController(EmpleadoService empleadoService) {
        this.empleadoService = empleadoService;
    }

    @PostMapping("/crearEmpleados")
    public void crearEmpleados(
            @Valid @RequestBody EmpleadoDto empleadoDto
    ) {
        empleadoService.createEmpleado(empleadoDto);
    }

    @GetMapping("/getEmpleados")
    public List<EmpleadoDto> getAll() {
        return empleadoService.findAll();
    }

    @PutMapping("/{id}")
    public EmpleadoDto actualizarEmpleados(
            @PathVariable Integer id, @Valid @RequestBody EmpleadoDto empleadoDto
    ) {
        EmpleadoDto empleadoUpdated = empleadoService.updateEmpleado(id, empleadoDto);
        return ResponseEntity.ok(empleadoUpdated);
    }


}
