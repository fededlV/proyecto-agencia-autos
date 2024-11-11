package org.fede.pruebas.services;

import org.fede.pruebas.dto.NotificacionDto;
import org.fede.pruebas.entities.Empleado;
import org.fede.pruebas.entities.Notificacion;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class NotificacionMapper {

    public NotificacionDto toNotificacionDto(Notificacion notificacion) {
        if (notificacion == null) {
            throw new NullPointerException("La notificación no debería ser nula");
        }
        return new NotificacionDto(
                notificacion.getEmpleado().getLegajo(),
                notificacion.getMensaje(),
                notificacion.getFechaEnvio(),
                notificacion.getEsIncidente()
        );
    }

    public Notificacion toNotificacion(NotificacionDto dto, Empleado empleado) {
        if (dto == null) {
            throw new NullPointerException("El DTO de notificación no debería ser nulo");
        }
        if (empleado == null) {
            throw new NullPointerException("El empleado no debería ser nulo");
        }
        return new Notificacion(
                empleado,
                dto.mensaje(),
                dto.fechaEnvio() != null ? dto.fechaEnvio() : LocalDateTime.now(),
                dto.esIncidente() != null ? dto.esIncidente() : false
        );
    }
}
