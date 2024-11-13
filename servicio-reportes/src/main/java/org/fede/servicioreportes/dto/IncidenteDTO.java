package org.fede.servicioreportes.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class IncidenteDTO {
    private Long pruebaId;
    private Long vehiculoId;
    private String descripcion;
    private LocalDateTime fechaIncidente;

    // Getters y Setters
}
