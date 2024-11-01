package org.fede.pruebas.services;

import jakarta.persistence.EntityNotFoundException;
import org.fede.pruebas.dto.PruebaDto;
import org.fede.pruebas.dto.PruebaResponseDto;
import org.fede.pruebas.entities.Empleado;
import org.fede.pruebas.entities.Interesado;
import org.fede.pruebas.entities.Prueba;
import org.fede.pruebas.entities.Vehiculo;
import org.fede.pruebas.repositories.EmpleadoRepository;
import org.fede.pruebas.repositories.InteresadoRepository;
import org.fede.pruebas.repositories.PruebaRepository;
import org.fede.pruebas.repositories.VehiculoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PruebasServices {

    private final PruebaRepository repository;
    private final PruebasMapper mapper;
    private final VehiculoRepository vehiculoRepository;
    private final EmpleadoRepository empleadoRepository;
    private final InteresadoRepository interesadoRepository;

    public PruebasServices(PruebaRepository repository, PruebasMapper mapper, VehiculoRepository vehiculoRepository, EmpleadoRepository empleadoRepository, InteresadoRepository interesadoRepository) {
        this.repository = repository;
        this.mapper = mapper;
        this.vehiculoRepository = vehiculoRepository;
        this.empleadoRepository = empleadoRepository;
        this.interesadoRepository = interesadoRepository;
    }

    public PruebaResponseDto create(PruebaDto dto) {
        //Convertir el DTO a la entidad
        Prueba prueba = mapper.toPrueba(dto);

        //Comprobar si el vehiculo esta en prueba
        boolean estaEnPrueba = repository.existsByVehiculoAndFechaHoraInicioLessThanEqualAndFechaHoraFinGreaterThanEqual(
                prueba.getVehiculo(),
                prueba.getFechaHoraInicio(),
                prueba.getFechaHoraFin()
        );

        if(estaEnPrueba) {
            throw new RuntimeException("El vehiculo ya esta siendo probado en este momento");
        }

        //Guarda la prueba
        var savedPrueba = repository.save(prueba);
        return mapper.toPruebaResponseDto(savedPrueba);
    }

    public List<PruebaResponseDto> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toPruebaResponseDto)//Se convierten todos los objetos prueba a el DTO response, para ser devuelto esos datos
                .collect(Collectors.toList());
    }

    public PruebaResponseDto updatePrueba(Integer id, PruebaDto dto) {
        //1. Primero se busca la prueba.
        Prueba prueba = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("La prueba no se encontro"));

        //2. se pasa a buscar los vehiculos
        Vehiculo vechiculo = vehiculoRepository.findById(dto.idVehiculo())
                        .orElseThrow(() -> new EntityNotFoundException("La vehiculo no se encontro"));
        Empleado empleado = empleadoRepository.findById(dto.idEmpleado())
                        .orElseThrow(() -> new EntityNotFoundException("El empleado no se encontro"));
        Interesado interesado = interesadoRepository.findById(dto.idInteresado())
                        .orElseThrow(() -> new EntityNotFoundException("El interesado no se encontro"));

        //Actualizar los datos.
        prueba.setComentarios(dto.comentario());
        prueba.setFechaHoraInicio(dto.fechaHoraInicio());
        prueba.setFechaHoraFin(dto.fechaHoraFin());
        prueba.setEmpleado(empleado);
        prueba.setInteresado(interesado);
        prueba.setVehiculo(vechiculo);

        //Guarda la entidad en la actualiza al DTO respuesta para devolverla.
        return mapper.toPruebaResponseDto(repository.save(prueba));
    }

    public void deletePrueba(Integer id) {
        repository.deleteById(id);
    }
}
