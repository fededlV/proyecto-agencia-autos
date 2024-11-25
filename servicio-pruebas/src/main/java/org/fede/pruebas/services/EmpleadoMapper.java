package org.fede.pruebas.services;

import org.fede.pruebas.dto.EmpleadoDto;
import org.fede.pruebas.entities.Empleado;
import org.springframework.stereotype.Service;

@Service
public class EmpleadoMapper {

    public EmpleadoDto toEmpleadoDto(Empleado empleado) {
        return new EmpleadoDto(
                empleado.getNombre(),
                empleado.getApellido(),
                empleado.getTelefono()
        );
    }

    public Empleado toEmpleado(EmpleadoDto dto) {
        Empleado empleado = new Empleado();
        empleado.setNombre(dto.nombre());
        empleado.setApellido(dto.apellido());
        empleado.setTelefono(dto.telefono_contacto());

        return empleado;
    }
}
