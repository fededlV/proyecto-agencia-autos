package org.fede.servicioubiynoti.controllers;

import org.fede.servicioubiynoti.services.NotificacionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/vehiculos")
public class VehiculoController {

    private final NotificacionService notificacionService;

    public VehiculoController(NotificacionService notificacionService) {
        this.notificacionService = notificacionService;
    }

    @PostMapping("/evaluar-posicion")
    public ResponseEntity<String> evaluarPosicion(@RequestBody Map<String, Object> posicion) {
        Double latitud = (Double) posicion.get("latitud");
        Double longitud = (Double) posicion.get("longitud");
        Integer empleadoLegajo = ((Number) posicion.get("empleadoLegajo")).intValue(); // Convertir a Integer

        boolean resultado = notificacionService.evaluarPosicionVehiculo(latitud, longitud, empleadoLegajo);

        if (resultado) {
            return ResponseEntity.ok("Notificación generada y almacenada.");
        } else {
            return ResponseEntity.ok("El vehículo está dentro de los límites permitidos.");
        }
    }
}

