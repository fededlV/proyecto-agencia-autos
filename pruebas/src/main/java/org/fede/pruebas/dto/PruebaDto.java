package org.fede.pruebas.dto;

import jakarta.validation.constraints.NotEmpty;

import java.time.LocalDateTime;

public record PruebaDto(
        @NotEmpty(message = "El ID del vehiculo no debe estar vacio")
        Integer idVehiculo,
        @NotEmpty(message = "El ID del interesado no debe estar vacio")
        Integer idInteresado,
        @NotEmpty(message = "El ID del empleado no debe estar vacio")
        Integer idEmpleado,
        @NotEmpty(message = "La fecha y hora inicio de la prueba no debe estar vacio")
        LocalDateTime fechaHoraInicio,
        @NotEmpty(message = "La fecha y hora fin de la prueba no debe estar vacio")
        LocalDateTime fechaHoraFin,
        String comentario
) {
}
