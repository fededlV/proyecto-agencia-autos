package org.fede.pruebas.services;

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

    public void createEmpleado(EmpleadoDto empleadoDto) {
        Empleado empleado = empleadoMapper.toEmpleado(empleadoDto);
        empleadoRepository.save(empleado);
    }

    public List<EmpleadoDto> findAll() {
        return empleadoRepository.findAll()
                .stream()
                .map(empleadoMapper::toEmpleadoDto)
                .collect(Collectors.toList());
    }
}
