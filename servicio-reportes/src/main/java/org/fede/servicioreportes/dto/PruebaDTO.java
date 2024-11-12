package org.fede.servicioreportes.dto;

import java.time.LocalDateTime;

public record PruebaDTO(
        Integer id,
        LocalDateTime fechaHoraInicio,
        LocalDateTime fechaHoraFin,
        Integer clienteId,
        Integer empleadoId,
        String comentario
) {
}
