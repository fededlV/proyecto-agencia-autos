package org.fede.pruebas.services;

import org.fede.pruebas.dto.ModeloDto;
import org.fede.pruebas.dto.ModeloResponseDto;
import org.fede.pruebas.entities.Marca;
import org.fede.pruebas.entities.Modelo;
import org.springframework.stereotype.Service;

@Service
public class ModeloMapper {

    public ModeloResponseDto toModeloResponseDto(Modelo modelo) {
        return new ModeloResponseDto(
                modelo.getDescripcion()
        );
    }

    public Modelo toModelo(ModeloDto modeloDto) {
        if(modeloDto == null) {
            throw new NullPointerException("El modelo no deberia ser nulo");
        }
        Modelo modelo = new Modelo();
        modelo.setDescripcion(modeloDto.descripcion());

        Marca marca = new Marca();
        marca.setId(modeloDto.id_marca());
        modelo.setMarca(marca);

        return modelo;
    }


}
