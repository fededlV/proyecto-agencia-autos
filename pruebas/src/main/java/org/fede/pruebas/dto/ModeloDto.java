package org.fede.pruebas.dto;

import jakarta.validation.constraints.NotEmpty;

public record ModeloDto(
        @NotEmpty(message = "El id de la marca no debe estar vacio")
        Integer id_marca,
        String descripcion
) {
}
