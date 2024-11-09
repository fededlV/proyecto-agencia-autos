package org.fede.pruebas.services;

import org.fede.pruebas.dto.NotificacionDto;
import org.fede.pruebas.entities.Interesado;
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
                notificacion.getInteresado().getId(),
                notificacion.getMensaje(),
                notificacion.getFechaEnvio()
        );
    }


    public Notificacion toNotificacion(NotificacionDto dto, Interesado interesado) {
        if (dto == null) {
            throw new NullPointerException("El DTO de notificación no debería ser nulo");
        }
        if (interesado == null) {
            throw new NullPointerException("El interesado no debería ser nulo");
        }
        return new Notificacion(
                interesado,
                dto.mensaje(),
                dto.fechaEnvio() != null ? dto.fechaEnvio() : LocalDateTime.now()
        );
    }

}
