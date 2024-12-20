package org.fede.pruebas.controllers;

import jakarta.validation.Valid;
import org.fede.pruebas.dto.PruebaDto;
import org.fede.pruebas.dto.PruebaFinalizadaDto;
import org.fede.pruebas.dto.PruebaResponseDto;
import org.fede.pruebas.services.PruebasServices;
import org.springframework.cglib.core.Local;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/pruebas")
public class PruebaController {

    private final PruebasServices services;

    public PruebaController(PruebasServices services) {
        this.services = services;
    }

    @GetMapping("/obtener")
    public List<PruebaResponseDto> findAll() {
        return this.services.findAll();
    }

    @GetMapping("/en-curso")
    public ResponseEntity<List<PruebaResponseDto>> listarPruebasEnCurso(
            @RequestParam(value = "fechaHora", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)LocalDateTime fechaHora
            ) {
        //Si no se proporciona la fecha, usar la fecha y hora actual
        LocalDateTime fechaConsulta = (fechaHora != null) ? fechaHora : LocalDateTime.now();

        //Llama al servicio que contiene la logica necesaria para este endpoint.
        List<PruebaResponseDto> pruebasEnCursoDtos =  services.listarPruebasEnCurso(fechaHora);
        return ResponseEntity.ok(pruebasEnCursoDtos);
    }

    @PostMapping("/crear")
    public ResponseEntity<PruebaResponseDto> createPrueba(
            @Valid @RequestBody PruebaDto pruebaDto
            ) {
        PruebaResponseDto pruebaCreate = services.create(pruebaDto);
        return new ResponseEntity<>(pruebaCreate, HttpStatus.CREATED);
    }

    @PutMapping("/finalizar")
    public ResponseEntity<PruebaResponseDto> finalizarPrueba(
            @Valid @RequestBody PruebaFinalizadaDto pruebaDto, @PathVariable Integer id
    ) {
        PruebaResponseDto pruebaFinalizada = services.finalizaPrueba(id, pruebaDto);
        return new ResponseEntity<>(pruebaFinalizada, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PruebaResponseDto> updatePrueba(
            @PathVariable Integer id, @Valid @RequestBody PruebaDto pruebaDto
    ) {
        PruebaResponseDto pruebaUpdate = services.updatePrueba(id, pruebaDto);
        return ResponseEntity.ok(pruebaUpdate);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePrueba(
            @PathVariable Integer id
    ) {
        services.deletePrueba(id);
        return ResponseEntity.noContent().build();
    }
}
