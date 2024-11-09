package org.fede.pruebas.services;

import jakarta.persistence.EntityNotFoundException;
import org.fede.pruebas.dto.PruebaDto;
import org.fede.pruebas.dto.PruebaFinalizadaDto;
import org.fede.pruebas.dto.PruebaResponseDto;
import org.fede.pruebas.entities.Empleado;
import org.fede.pruebas.entities.Interesado;
import org.fede.pruebas.entities.Prueba;
import org.fede.pruebas.entities.Vehiculo;
import org.fede.pruebas.repositories.EmpleadoRepository;
import org.fede.pruebas.repositories.InteresadoRepository;
import org.fede.pruebas.repositories.PruebaRepository;
import org.fede.pruebas.repositories.VehiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PruebasServices {

    private final PruebaRepository repository;
    private final PruebasMapper mapper;
    private final VehiculoRepository vehiculoRepository;
    private final EmpleadoRepository empleadoRepository;
    private final InteresadoRepository interesadoRepository;
    private final InteresadoService interesadoService;

    public PruebasServices(
            PruebaRepository repository,
            PruebasMapper mapper,
            VehiculoRepository vehiculoRepository,
            EmpleadoRepository empleadoRepository,
            InteresadoRepository interesadoRepository,
            InteresadoService interesadoService) {
        this.repository = repository;
        this.mapper = mapper;
        this.vehiculoRepository = vehiculoRepository;
        this.empleadoRepository = empleadoRepository;
        this.interesadoRepository = interesadoRepository;
        this.interesadoService = interesadoService;
    }

    public PruebaResponseDto create(PruebaDto dto) {

        //Validar que el interesado cumpla los requisitos para realizar la prueba 
        interesadoService.validarInteresado(dto.idInteresado());

        //Buscar las entidades relacionadas
        Vehiculo vehiculo = vehiculoRepository.findById(dto.idVehiculo())
                .orElseThrow(() -> new EntityNotFoundException("El vehiculo no se encontro"));

        Empleado empleado = empleadoRepository.findById(dto.idEmpleado())
                .orElseThrow(() -> new EntityNotFoundException("El empleado no se encontro"));

        Interesado interesado = interesadoRepository.findById(dto.idInteresado())
                .orElseThrow(() -> new EntityNotFoundException("El interesado no se encontro"));

        //Convertir el DTO a la entidad
        Prueba prueba = mapper.toPrueba(dto);

        //Asignar entidades relacionadas 
        prueba.setVehiculo(vehiculo);
        prueba.setEmpleado(empleado);
        prueba.setInteresado(interesado);

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

        //Convierte a DTO para devolverlo
        return mapper.toPruebaResponseDto(savedPrueba);
    }

    public List<PruebaResponseDto> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toPruebaResponseDto)//Se convierten todos los objetos prueba a el DTO response, para ser devuelto esos datos
                .collect(Collectors.toList());
    }

    public List<PruebaResponseDto> listarPruebasEnCurso(LocalDateTime fechaHora) {
        //Llama al repositorio
        List<Prueba> pruebasEnCurso = repository.findByPruebasEnCurso(fechaHora);

        //Convierte las pruebas en DTO
        return pruebasEnCurso.stream()
                .map(mapper::toPruebaResponseDto)
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

    public void finalizaPrueba(Integer id, PruebaFinalizadaDto dto) {
        //Buscar la prueba por ID
        Prueba prueba = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("La prueba no se encontro"));

        //Verificar que la prueba este en curso
        if(prueba.getFechaHoraFin() != null) {
            throw new RuntimeException("La prueba ya ha finalizado");
        }

        //Buscar el empleado y verificar existencia
        Empleado empleado = empleadoRepository.findById(dto.empleadoId())
                .orElseThrow(() -> new EntityNotFoundException("El empleado no se encontro"));

        //Actualizar la fecha de finalizacion y el comnentario
        prueba.setFechaHoraFin(LocalDateTime.now());
        prueba.setComentarios(dto.comentario());

        //Guardar datos
        repository.save(prueba);
    }
}
