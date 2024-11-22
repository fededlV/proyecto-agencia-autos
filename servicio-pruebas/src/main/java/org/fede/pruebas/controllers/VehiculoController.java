package org.fede.pruebas.controllers;

import jakarta.validation.Valid;
import org.fede.pruebas.dto.VehiculoDto;
import org.fede.pruebas.dto.VehiculoResponseDto;
import org.fede.pruebas.services.VehiculoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehiculos")
public class VehiculoController {

    private final VehiculoService service;

    public VehiculoController(VehiculoService service) {
        this.service = service;
    }

    @GetMapping("/obtener")
    public ResponseEntity<List<VehiculoResponseDto>> getVehiculos() {
        return ResponseEntity.ok(service.findAll());
    }

    @PostMapping("/crear")
    public ResponseEntity<VehiculoResponseDto> crearVehiculo(
            @Valid @RequestBody VehiculoDto vehiculoDto
            ) {
        VehiculoResponseDto vehiculoCreate = service.createVehiculo(vehiculoDto);
        return new ResponseEntity<>(vehiculoCreate, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VehiculoResponseDto> actualizarVehiculo(
            @PathVariable Integer id, @Valid @RequestBody VehiculoDto vehiculoDto
            ) {
        VehiculoResponseDto vehiculoUpdate = service.updateVehiculo(id, vehiculoDto);
        return ResponseEntity.ok(vehiculoUpdate);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> borrarVehiculo(
            @PathVariable Integer id
    ) {
        service.deleteVehiculo(id);
        return ResponseEntity.noContent().build();
    }
}
