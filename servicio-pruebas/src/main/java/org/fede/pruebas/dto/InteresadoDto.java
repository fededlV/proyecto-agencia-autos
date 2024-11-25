package org.fede.pruebas.dto;

import java.time.LocalDateTime;

public record InteresadoDto(
        String tipo_documento,
        String documento,
        String nombre,
        String apellido,
        boolean restringido,
        Integer nro_licencia,
        LocalDateTime fechaVencimientoLic
) {
}
