package org.fede.servicioreportes.dto;

import java.time.LocalDateTime;

public record PruebaResponseDTO(
        LocalDateTime fechaHoraInicio,
        LocalDateTime fechaHoraFin,
        String comentario
) {
}
