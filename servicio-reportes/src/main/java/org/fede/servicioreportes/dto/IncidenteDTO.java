package org.fede.servicioreportes.dto;

import org.fede.servicioreportes.entities.Coordenada;

public record IncidenteDTO(
        Long pruebaId,
        Long clienteId,
        Long vehiculoId,
        Coordenada posicion,
        double distanciaExcedida,
        String descripcion
) {
}
