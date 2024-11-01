package org.fede.pruebas.controllers;

import jakarta.validation.Valid;
import org.fede.pruebas.dto.InteresadoDto;
import org.fede.pruebas.services.InteresadoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InteresadoController {

    private final InteresadoService interesadoService;

    public InteresadoController(InteresadoService interesadoService) {
        this.interesadoService = interesadoService;
    }

    @PostMapping("/interesados")
    public ResponseEntity<Void> createInteresado(
            @Valid @RequestBody InteresadoDto interesadoDto
            ) {
        interesadoService.createInteresado(interesadoDto); //Logica de la creacion que esta en el service
        return ResponseEntity.status(HttpStatus.CREATED).build(); //Devuelve un 201 created
    }
}
