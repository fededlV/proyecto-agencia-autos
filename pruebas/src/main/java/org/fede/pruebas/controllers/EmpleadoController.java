package org.fede.pruebas.controllers;

import jakarta.validation.Valid;
import org.fede.pruebas.dto.EmpleadoDto;
import org.fede.pruebas.entities.Empleado;
import org.fede.pruebas.services.EmpleadoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/empleados")
public class EmpleadoController {

    private final EmpleadoService empleadoService;

    public EmpleadoController(EmpleadoService empleadoService) {
        this.empleadoService = empleadoService;
    }

    @GetMapping("/obtener")
    public List<EmpleadoDto> getAll() {
        return empleadoService.findAll();
    }

    @PostMapping("/crearEmpleados")
    public ResponseEntity<EmpleadoDto> crearEmpleados(
            @Valid @RequestBody EmpleadoDto empleadoDto
    ) {
        empleadoService.createEmpleado(empleadoDto);
        return new ResponseEntity<>(empleadoDto, HttpStatus.CREATED);
    }


    @PutMapping("/{id}")
    public ResponseEntity<EmpleadoDto> actualizarEmpleados(
            @PathVariable Integer id, @Valid @RequestBody EmpleadoDto empleadoDto
    ) {
        EmpleadoDto empleadoUpdated = empleadoService.updateEmpleado(id, empleadoDto);
        return ResponseEntity.ok(empleadoUpdated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmpleado(
            @PathVariable Integer id
    ) {
        empleadoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }


}
