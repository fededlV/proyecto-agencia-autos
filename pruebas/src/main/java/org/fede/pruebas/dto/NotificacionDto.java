package org.fede.pruebas.dto;

import java.time.LocalDateTime;

public record NotificacionDto(
        Integer interesadoId,
        String mensaje,
        LocalDateTime fechaEnvio
) {
}
