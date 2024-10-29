package org.fede.pruebas.services;

import org.fede.pruebas.dto.PruebaDto;
import org.fede.pruebas.dto.PruebaResponseDto;
import org.fede.pruebas.entities.Empleados;
import org.fede.pruebas.entities.Interesados;
import org.fede.pruebas.entities.Pruebas;
import org.fede.pruebas.entities.Vehiculos;
import org.springframework.stereotype.Service;

@Service
public class PruebasMapper {

    public PruebaResponseDto toPruebaResponseDto(Pruebas prueba){
        return new PruebaResponseDto(
                prueba.getFecha_hora_inicio(),
                prueba.getFecha_hora_fin(),
                prueba.getComentarios()
        );
    }

    public Pruebas toPrueba(PruebaDto dto){
        if(dto == null) {
            throw new NullPointerException("La prueba no debe ser nula");
        }
        Pruebas prueba = new Pruebas();
        prueba.setFecha_hora_fin(dto.fechaHoraFin());
        prueba.setFecha_hora_inicio(dto.fechaHoraInicio());
        prueba.setComentarios(dto.comentario());

        Empleados empleado = new Empleados();
        empleado.setLegajo(dto.idEmpleado());
        Vehiculos vehiculo = new Vehiculos();
        vehiculo.setId(dto.idVehiculo());
        Interesados interesado = new Interesados();
        interesado.setId(dto.idInteresado());

        prueba.setEmpleado(empleado);
        prueba.setVehiculo(vehiculo);
        prueba.setInteresado(interesado);

        return prueba;
    }
}
