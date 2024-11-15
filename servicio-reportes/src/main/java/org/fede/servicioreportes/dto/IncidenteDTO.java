package org.fede.servicioreportes.dto;

import org.fede.servicioreportes.entities.Posicion;

public record IncidenteDTO(
        Integer pruebaId,
        Integer vehiculoId,
        Integer clienteId,
        Posicion posicion,
        String descripcion
) {
}
