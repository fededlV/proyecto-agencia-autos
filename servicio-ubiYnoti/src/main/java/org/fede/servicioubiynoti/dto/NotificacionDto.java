package org.fede.servicioubiynoti.dto;

import java.time.LocalDateTime;

public record NotificacionDto(
        Integer empleadoId,
        String mensaje,
        LocalDateTime fechaEnvio,
        Boolean esIncidente
) {
}
