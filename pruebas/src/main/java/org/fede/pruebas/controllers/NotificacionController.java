package org.fede.pruebas.controllers;

import jakarta.validation.Valid;
import org.fede.pruebas.dto.NotificacionDto;
import org.fede.pruebas.services.NotificacionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notificaciones")
public class NotificacionController {

    private final NotificacionService notificacionService;

    public NotificacionController(NotificacionService notificacionService) {
        this.notificacionService = notificacionService;
    }

    @PostMapping("/crear")
    public ResponseEntity<NotificacionDto> createNotificacion(@Valid @RequestBody NotificacionDto dto) {
        NotificacionDto createdNotificacion = notificacionService.createNotificacion(dto);
        return new ResponseEntity<>(createdNotificacion, HttpStatus.CREATED);
    }

    @GetMapping("/obtener")
    public List<NotificacionDto> getNotificaciones() {
        return notificacionService.findAll();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNotificacion(@PathVariable Integer id) {
        notificacionService.deleteNotificacion(id);
        return ResponseEntity.noContent().build();
    }
}
