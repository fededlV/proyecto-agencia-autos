package org.fede.pruebas.controllers;

import jakarta.validation.Valid;
import org.fede.pruebas.dto.ModeloDto;
import org.fede.pruebas.dto.ModeloResponseDto;
import org.fede.pruebas.services.ModeloService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/modelos")
public class ModeloController {

    private final ModeloService service;

    public ModeloController(ModeloService service) {
        this.service = service;
    }

    @PostMapping("/api/crear")
    public ResponseEntity<ModeloResponseDto> crearModelo(
            @Valid @RequestBody ModeloDto modeloDto
            ) {
        ModeloResponseDto modeloSaved = service.createModelo(modeloDto);
        return new ResponseEntity<>(modeloSaved, HttpStatus.CREATED);
    }

    @GetMapping("/obtener")
    public List<ModeloResponseDto> obtenerModelos() {
        return service.findAll();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ModeloResponseDto> modificarModelo(
            @PathVariable Integer id, @Valid @RequestBody ModeloDto modeloDto
    ) {
        ModeloResponseDto modeloUpdate = service.updateModelo(id, modeloDto);
        return ResponseEntity.ok(modeloUpdate);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarModelo(
            @PathVariable Integer id
    ) {
        service.deleteModelo(id);
        return ResponseEntity.noContent().build();
    }
}
