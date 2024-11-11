package org.fede.pruebas.dto;

import java.time.LocalDateTime;

public record NotificacionDto(
        Integer empleadoId,
        String mensaje,
        LocalDateTime fechaEnvio,
        Boolean esIncidente
) {
}
