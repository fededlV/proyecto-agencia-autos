package org.fede.pruebas.dto;

import jakarta.validation.constraints.NotEmpty;

public record VehiculoDto(
        String patente,
        @NotEmpty(message = "El id del modelo no debe estar vacio")
        Integer id_modelo
) {
}
