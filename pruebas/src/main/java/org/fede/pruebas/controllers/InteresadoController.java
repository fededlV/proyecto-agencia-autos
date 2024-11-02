package org.fede.pruebas.controllers;

import jakarta.validation.Valid;
import org.fede.pruebas.dto.InteresadoDto;
import org.fede.pruebas.entities.Interesado;
import org.fede.pruebas.services.InteresadoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/interesados")
public class InteresadoController {

    private final InteresadoService interesadoService;

    public InteresadoController(InteresadoService interesadoService) {
        this.interesadoService = interesadoService;
    }

    @PostMapping("/crear")
    public ResponseEntity<InteresadoDto> createInteresado(
            @Valid @RequestBody InteresadoDto interesadoDto
    ) {
        interesadoService.createInteresado(interesadoDto);
        return new ResponseEntity<>(interesadoDto, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InteresadoDto> updateInteresado(
            @PathVariable Integer id, @Valid @RequestBody InteresadoDto interesadoDto
    ) {
        InteresadoDto interesadoUpdate = interesadoService.updateInteresado(id, interesadoDto);
        return ResponseEntity.ok(interesadoUpdate);
    }

    @GetMapping("/obtener")
    public List<InteresadoDto> getInteresados() {
        return interesadoService.findAll();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInteresado(
            @PathVariable Integer id
    ) {
        interesadoService.deleteInteresado(id);
        return ResponseEntity.noContent().build();
    }
}
