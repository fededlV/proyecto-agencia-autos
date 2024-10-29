package org.fede.pruebas.services;

import org.fede.pruebas.dto.VehiculoDto;
import org.fede.pruebas.dto.VehiculoResponseDto;
import org.fede.pruebas.entities.Modelo;
import org.fede.pruebas.entities.Vehiculo;
import org.springframework.stereotype.Service;

@Service
public class VehiculoMapper {

    public VehiculoResponseDto toVehiculoResponseDto(Vehiculo vehiculo) {
        return new VehiculoResponseDto(
                vehiculo.getPatente()
        );
    }

    public Vehiculo toVehiculo(VehiculoDto dto) {
        if(dto == null) {
            throw new NullPointerException("El vehiculo dto no deberia ser nulo");
        }
        Vehiculo vehiculo = new Vehiculo();
        vehiculo.setPatente(dto.patente());

        Modelo modelo = new Modelo();
        modelo.setId(dto.id_modelo());

        vehiculo.setModelo(modelo);

        return vehiculo;
    }
}
