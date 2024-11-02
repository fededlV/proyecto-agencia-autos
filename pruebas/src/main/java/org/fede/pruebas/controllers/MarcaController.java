package org.fede.pruebas.controllers;

import jakarta.validation.Valid;
import org.fede.pruebas.dto.MarcaDto;
import org.fede.pruebas.services.MarcaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/marcas")
public class MarcaController {

    private final MarcaService marcaService;

    public MarcaController(MarcaService marcaService) {
        this.marcaService = marcaService;
    }

    @GetMapping("/obtener")
    public List<MarcaDto> obtenerMarcas() {
        return marcaService.findAll();
    }

    @PostMapping("/crear")
    public ResponseEntity<MarcaDto> crearMarca(
            @Valid @RequestBody MarcaDto marcaDto
    ) {
        marcaService.createMarca(marcaDto);
        return new ResponseEntity<>(marcaDto, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MarcaDto> actualizarMarca(
            @PathVariable Integer id, @Valid @RequestBody MarcaDto marcaDto
    ) {
        MarcaDto marcaUpdate = marcaService.updateMarca(id, marcaDto);
        return ResponseEntity.ok(marcaUpdate);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarMarca(
            @PathVariable Integer id
    ) {
        marcaService.deleteMarca(id);
        return ResponseEntity.noContent().build();
    }
}
