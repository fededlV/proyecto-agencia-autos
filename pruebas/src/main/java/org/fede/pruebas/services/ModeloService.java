package org.fede.pruebas.services;

import org.fede.pruebas.dto.ModeloResponseDto;
import org.fede.pruebas.repositories.ModeloRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ModeloService {

    private final ModeloRepository repository;
    private final ModeloMapper mapper;

    public ModeloService(ModeloRepository repository, ModeloMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<ModeloResponseDto> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toModeloResponseDto)
                .collect(Collectors.toList());
    }
}
