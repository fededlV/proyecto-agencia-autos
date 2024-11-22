package org.fede.pruebas.services;

import org.fede.pruebas.dto.MarcaDto;
import org.fede.pruebas.entities.Marca;
import org.springframework.stereotype.Service;

@Service
public class MarcaMapper {

    public MarcaDto toMarcaDto(Marca marca) {
        return new MarcaDto(
                marca.getNombre()
        );
    }

    public Marca toMarca(MarcaDto dto) {
        Marca marca = new Marca();
        marca.setNombre(dto.nombre());

        return marca;
    }
}
