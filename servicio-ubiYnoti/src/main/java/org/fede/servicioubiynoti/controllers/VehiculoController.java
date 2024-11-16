package org.fede.servicioubiynoti.controllers;

import org.fede.servicioubiynoti.dto.PosicionDto;
import org.fede.servicioubiynoti.services.ValidacionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vehiculos")
public class VehiculoController {

    private final ValidacionService validacionService;

    public VehiculoController(ValidacionService validacionService) {
        this.validacionService = validacionService;
    }

    @PostMapping("/validar-posicion")
    public ResponseEntity<String> validarPosicion(@RequestBody PosicionDto posicionDto) {
        boolean valida = validacionService.validarPosicion(posicionDto);

        if (valida) {
            return ResponseEntity.ok("La posición es válida.");
        } else {
            return ResponseEntity.badRequest().body("La posición no es válida.");
        }
    }
}
