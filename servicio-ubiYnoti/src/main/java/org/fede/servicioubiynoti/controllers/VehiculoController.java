package org.fede.servicioubiynoti.controllers;

import org.fede.servicioubiynoti.services.NotificacionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
        Integer id_vehiculo = ((Number) posicion.get("id_vehiculo")).intValue();
        String fechaHoraStr = (String) posicion.get("fecha_hora");
        LocalDateTime fechaHora = LocalDateTime.parse(fechaHoraStr, DateTimeFormatter.ISO_DATE_TIME);
        Double latitud = ((Number) posicion.get("latitud")).doubleValue();
        Double longitud = ((Number) posicion.get("longitud")).doubleValue();

        boolean resultado = notificacionService.evaluarPosicionVehiculo(id_vehiculo, fechaHora, latitud, longitud);

        if (resultado) {
            return ResponseEntity.ok("Notificación generada y almacenada.");
        } else {
            return ResponseEntity.ok("El vehículo está dentro de los límites permitidos.");
        }
    }
}

