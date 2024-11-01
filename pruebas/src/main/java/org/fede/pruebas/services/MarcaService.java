package org.fede.pruebas.services;

import org.fede.pruebas.dto.InteresadoDto;
import org.fede.pruebas.dto.MarcaDto;
import org.fede.pruebas.repositories.MarcaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MarcaService {

    private final MarcaRepository repository;
    private final MarcaMapper mapper;

    public MarcaService(MarcaRepository repository, MarcaMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<MarcaDto> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toMarcaDto)
                .collect(Collectors.toList());
    }
}
