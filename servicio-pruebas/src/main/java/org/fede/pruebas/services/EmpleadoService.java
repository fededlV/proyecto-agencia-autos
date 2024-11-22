package org.fede.pruebas.services;

import jakarta.persistence.EntityNotFoundException;
import org.fede.pruebas.dto.EmpleadoDto;
import org.fede.pruebas.entities.Empleado;
import org.fede.pruebas.repositories.EmpleadoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmpleadoService {

    private final EmpleadoMapper empleadoMapper;
    private final EmpleadoRepository empleadoRepository;

    public EmpleadoService(EmpleadoMapper empleadoMapper, EmpleadoRepository empleadoRepository) {
        this.empleadoMapper = empleadoMapper;
        this.empleadoRepository = empleadoRepository;
    }

    public EmpleadoDto createEmpleado(EmpleadoDto empleadoDto) {
        Empleado empleado = empleadoMapper.toEmpleado(empleadoDto);
        Empleado createEmpleado = empleadoRepository.save(empleado);
        return empleadoMapper.toEmpleadoDto(createEmpleado);
    }

    public List<EmpleadoDto> findAll() {
        return empleadoRepository.findAll()
                .stream()
                .map(empleadoMapper::toEmpleadoDto)
                .collect(Collectors.toList());
    }

    public EmpleadoDto findById(Integer legajo) {
        return empleadoRepository.findById(legajo)
                .map(empleadoMapper::toEmpleadoDto)
                .orElse(null);
    }

    public EmpleadoDto findByName(String name) {
        return empleadoRepository.findByNombre(name)
                .map(empleadoMapper::toEmpleadoDto)
                .orElse(null);
    }

    public void deleteById(Integer legajo) {
        empleadoRepository.deleteById(legajo);
    }

    public EmpleadoDto updateEmpleado(Integer legajo, EmpleadoDto empleadoDto) {
        Empleado empleado = empleadoRepository.findById(legajo)
                .orElseThrow(() -> new EntityNotFoundException("El empleado no fue encontrado con legajo: " + legajo));

        empleado.setTelefono(empleadoDto.telefono_contacto());
        empleado.setNombre(empleadoDto.nombre());
        empleado.setApellido(empleadoDto.apellido());

        Empleado empleadoUpdate = empleadoRepository.save(empleado);

        return empleadoMapper.toEmpleadoDto(empleadoUpdate);
    }
}
