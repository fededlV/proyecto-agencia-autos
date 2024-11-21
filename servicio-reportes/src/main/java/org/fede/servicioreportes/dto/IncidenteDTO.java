package org.fede.servicioreportes.dto;

import org.fede.servicioreportes.model.Coordenada;

public record IncidenteDTO(
        Integer pruebaId,
        Integer vehiculoId,
        Integer clienteId,
        Coordenada posicion,
        String descripcion
) {
}
