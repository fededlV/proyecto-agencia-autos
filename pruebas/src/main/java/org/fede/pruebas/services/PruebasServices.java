package org.fede.pruebas.services;

import org.fede.pruebas.entities.Pruebas;
import org.fede.pruebas.repositories.PruebaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PruebasServices {

    private final PruebaRepository repository;

    public PruebasServices(PruebaRepository repository) {
        this.repository = repository;
    }

    public List<Pruebas> findAll() {
        return repository.findAll();
    }
}
