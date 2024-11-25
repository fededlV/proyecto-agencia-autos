package org.fede.pruebas.services;

import org.fede.pruebas.dto.PruebaDto;
import org.fede.pruebas.dto.PruebaResponseDto;
import org.fede.pruebas.entities.Empleado;
import org.fede.pruebas.entities.Interesado;
import org.fede.pruebas.entities.Prueba;
import org.fede.pruebas.entities.Vehiculo;
import org.springframework.stereotype.Service;

@Service
public class PruebasMapper {

    public PruebaResponseDto toPruebaResponseDto(Prueba prueba){
        if(prueba == null) {
            throw new NullPointerException("La prueba no deberia ser nula");
        }
        return new PruebaResponseDto(
                prueba.getFechaHoraInicio(),
                prueba.getFechaHoraFin(),
                prueba.getComentarios()
        );
    }

    public Prueba toPrueba(PruebaDto dto){
        if(dto == null) {
            throw new NullPointerException("La prueba no debe ser nula");
        }
        Prueba prueba = new Prueba();
        prueba.setFechaHoraFin(dto.fechaHoraFin());
        prueba.setFechaHoraInicio(dto.fechaHoraInicio());
        prueba.setComentarios(dto.comentario());

        Empleado empleado = new Empleado();
        empleado.setLegajo(dto.idEmpleado());
        Vehiculo vehiculo = new Vehiculo();
        vehiculo.setId(dto.idVehiculo());
        Interesado interesado = new Interesado();
        interesado.setId(dto.idInteresado());

        prueba.setEmpleado(empleado);
        prueba.setVehiculo(vehiculo);
        prueba.setInteresado(interesado);

        return prueba;
    }
}
