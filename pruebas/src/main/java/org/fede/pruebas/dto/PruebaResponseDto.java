package org.fede.pruebas.dto;

import java.time.LocalDateTime;

public record PruebaResponseDto(
        LocalDateTime fechaHoraInicio,
        LocalDateTime fechaHoraFin,
        String comentario
        ) {
}
