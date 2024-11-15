package org.fede.servicioubiynoti.dto;

import java.time.LocalDateTime;

public record PruebaResponseDTO(
        LocalDateTime fechaHoraInicio,
        LocalDateTime fechaHoraFin,
        String comentario
) {
}
