package org.fede.pruebas.services;

import jakarta.persistence.EntityNotFoundException;
import org.fede.pruebas.dto.InteresadoDto;
import org.fede.pruebas.dto.MarcaDto;
import org.fede.pruebas.entities.Marca;
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

    public MarcaDto createMarca(MarcaDto dto) {
        Marca marca = mapper.toMarca(dto);
        Marca marcaSaved = repository.save(marca);
        return mapper.toMarcaDto(marcaSaved);
    }

    public List<MarcaDto> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toMarcaDto)
                .collect(Collectors.toList());
    }

    public void deleteMarca(Integer id) {
        repository.deleteById(id);
    }

    public MarcaDto updateMarca(Integer id, MarcaDto dto) {
        Marca marca = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Marca no econtrada con ese id: " + id));

        marca.setNombre(dto.nombre());
        return mapper.toMarcaDto(repository.save(marca));
    }
}
