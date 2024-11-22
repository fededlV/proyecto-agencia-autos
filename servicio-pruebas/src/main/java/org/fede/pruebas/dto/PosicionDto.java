package org.fede.pruebas.dto;

import java.time.LocalDateTime;

public record PosicionDto(
        LocalDateTime fechaHora,
        Double longitud,
        Double latitud,
        Integer vehiculoId
) {
}
