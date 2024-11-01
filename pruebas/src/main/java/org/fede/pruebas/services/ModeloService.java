package org.fede.pruebas.services;

import jakarta.persistence.EntityNotFoundException;
import org.fede.pruebas.dto.ModeloDto;
import org.fede.pruebas.dto.ModeloResponseDto;
import org.fede.pruebas.entities.Marca;
import org.fede.pruebas.entities.Modelo;
import org.fede.pruebas.repositories.MarcaRepository;
import org.fede.pruebas.repositories.ModeloRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ModeloService {

    private final ModeloRepository repository;
    private final ModeloMapper mapper;
    private final MarcaRepository marcaRepository;

    public ModeloService(ModeloRepository repository, ModeloMapper mapper, MarcaRepository marcaRepository) {
        this.repository = repository;
        this.mapper = mapper;
        this.marcaRepository = marcaRepository;
    }

    public ModeloResponseDto createModelo(ModeloDto dto) {
        Modelo modelo = mapper.toModelo(dto);
        repository.save(modelo);
        return mapper.toModeloResponseDto(modelo);
    }

    public List<ModeloResponseDto> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toModeloResponseDto)
                .collect(Collectors.toList());
    }

    public ModeloResponseDto updateModelo(Integer id, ModeloDto dto) {
        //Busqueda de las entidades
        Modelo modelo = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("El modelo no existe"));

        Marca marca = marcaRepository.findById(dto.id_marca())
                        .orElseThrow(() -> new EntityNotFoundException("La marca no se encontro"));

        modelo.setMarca(marca);
        modelo.setDescripcion(dto.descripcion());

        return mapper.toModeloResponseDto(repository.save(modelo));
    }

    public void deleteModelo(Integer id) {
        repository.deleteById(id);
    }
}
