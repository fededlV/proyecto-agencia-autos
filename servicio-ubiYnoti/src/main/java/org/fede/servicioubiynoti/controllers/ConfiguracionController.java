package org.fede.servicioubiynoti.controllers;

import org.fede.servicioubiynoti.dto.PosicionDto;
import org.fede.servicioubiynoti.services.ValidacionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vehiculos")
public class VehiculoController {

    private final ValidacionService validacionService;

    public VehiculoController(ValidacionService validacionService) {
        this.validacionService = validacionService;
    }

    @PostMapping("/{vehiculoId}/posicion")
    public ResponseEntity<String> registrarPosicion(
            @PathVariable Integer vehiculoId,
            @RequestBody PosicionDto posicionDto) {
        boolean result = validacionService.validarPosicion(vehiculoId, posicionDto);
        if (result) {
            return ResponseEntity.ok("Posición registrada y validada.");
        } else {
            return ResponseEntity.badRequest().body("El vehículo infringió las reglas.");
        }
    }
}
