package org.fede.pruebas.services;

import org.fede.pruebas.entities.Pruebas;
import org.fede.pruebas.repositories.PruebaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PruebasServices {

    private final PruebaRepository repository;
    private final PruebasMapper mapper;

    public PruebasServices(PruebaRepository repository, PruebasMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }



    public List<Pruebas> findAll() {
        return repository.findAll();
    }
}
