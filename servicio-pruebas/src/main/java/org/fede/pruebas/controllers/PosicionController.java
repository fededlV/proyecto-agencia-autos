package org.fede.pruebas.controllers;

import jakarta.servlet.ServletResponse;
import jakarta.validation.Valid;
import org.fede.pruebas.dto.PosicionDto;
import org.fede.pruebas.dto.PosicionResponseDto;
import org.fede.pruebas.services.EmpleadoService;
import org.fede.pruebas.services.PosicionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posiciones")
public class PosicionController {

    private final PosicionService posicionService;
    private final EmpleadoService empleadoService;

    public PosicionController(PosicionService posicionService, EmpleadoService empleadoService) {
        this.posicionService = posicionService;
        this.empleadoService = empleadoService;
    }

    @GetMapping("/obtener")
    public List<PosicionResponseDto> getPosiciones() {
        return posicionService.findAll();
    }

    @PostMapping("/crear")
    public ResponseEntity<PosicionResponseDto> crearPosicion(
            @Valid @RequestBody PosicionDto posicionDto) {
        PosicionResponseDto posicionSaved = posicionService.createPosicion(posicionDto);
        return new ResponseEntity<>(posicionSaved, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PosicionResponseDto> actualizarPosicion(
            @PathVariable Integer id, @Valid @RequestBody PosicionDto posicionDto
    ) {
        PosicionResponseDto posicionUpload = posicionService.updatePosicion(id, posicionDto);
        return ResponseEntity.ok(posicionUpload);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarModelo(
            @PathVariable Integer id
    ) {
        empleadoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
