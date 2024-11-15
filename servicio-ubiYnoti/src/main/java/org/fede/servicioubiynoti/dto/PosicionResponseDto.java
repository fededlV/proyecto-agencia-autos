package org.fede.servicioubiynoti.dto;

import java.time.LocalDateTime;

public record PosicionResponseDto(
        LocalDateTime fechaHora,
        Double latitud,
        Double longitud
) {
}
