package org.fede.servicioreportes.services;

import org.fede.servicioreportes.dto.PruebaDTO;
import org.fede.servicioreportes.dto.PruebaResponseDTO;
import org.fede.servicioreportes.entities.Cliente;
import org.fede.servicioreportes.entities.Empleado;
import org.fede.servicioreportes.entities.Prueba;
import org.fede.servicioreportes.entities.Vehiculo;
import org.springframework.stereotype.Service;

@Service
public class PruebasMapper {
    public PruebaResponseDTO toPruebaResponseDto(Prueba prueba){
        if(prueba == null) {
            throw new NullPointerException("La prueba no deberia ser nula");
        }
        return new PruebaResponseDTO(
                prueba.getFechaHoraInicio(),
                prueba.getFechaHoraFin(),
                prueba.getComentarios()
        );
    }

    public Prueba toPrueba(PruebaDTO dto){
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
        Cliente cliente = new Cliente();
        cliente.setId(dto.idInteresado());

        prueba.setEmpleado(empleado);
        prueba.setVehiculo(vehiculo);
        prueba.setInteresado(cliente);

        return prueba;
    }
}
