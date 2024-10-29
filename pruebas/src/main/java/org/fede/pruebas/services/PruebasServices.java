package org.fede.pruebas.services;

import org.fede.pruebas.dto.PruebaDto;
import org.fede.pruebas.dto.PruebaResponseDto;
import org.fede.pruebas.entities.Prueba;
import org.fede.pruebas.repositories.PruebaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PruebasServices {

    private final PruebaRepository repository;
    private final PruebasMapper mapper;

    public PruebasServices(PruebaRepository repository, PruebasMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
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

    public List<Prueba> findAll() {
        return repository.findAll();
    }
}
