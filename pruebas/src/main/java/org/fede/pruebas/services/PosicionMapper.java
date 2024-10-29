package org.fede.pruebas.services;

import org.fede.pruebas.dto.PosicionDto;
import org.fede.pruebas.dto.PosicionResponseDto;
import org.fede.pruebas.entities.Posicion;
import org.fede.pruebas.entities.Vehiculo;
import org.springframework.stereotype.Service;

@Service
public class PosicionMapper {

    public PosicionResponseDto toPosicionResponseDto(Posicion posicion) {
        return new PosicionResponseDto(
                posicion.getFechaHora(),
                posicion.getLatitud(),
                posicion.getLongitud()
        );
    }

    public Posicion toPosicion(PosicionDto dto) {
        if(dto == null) {
            throw new NullPointerException("La posicion dto no deberia ser nula");
        }
        Posicion posicion = new Posicion();
        posicion.setFechaHora(dto.fechaHora());
        posicion.setLatitud(dto.latitud());
        posicion.setLongitud(dto.longitud());

        Vehiculo vehiculo = new Vehiculo();
        vehiculo.setId(dto.vehiculoId());

        posicion.setVehiculo(vehiculo);

        return posicion;
    }
}
