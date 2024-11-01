package org.fede.pruebas.services;

import jakarta.persistence.EntityNotFoundException;
import org.fede.pruebas.dto.PosicionDto;
import org.fede.pruebas.dto.PosicionResponseDto;
import org.fede.pruebas.entities.Posicion;
import org.fede.pruebas.repositories.PosicionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PosicionService {

    private final PosicionRepository posicionRepository;
    private final PosicionMapper posicionMapper;

    public PosicionService(PosicionRepository posicionRepository, PosicionMapper posicionMapper) {
        this.posicionRepository = posicionRepository;
        this.posicionMapper = posicionMapper;
    }

    public PosicionResponseDto createPosicion(PosicionDto dto){
        Posicion posicion = posicionMapper.toPosicion(dto);
        Posicion posicionSaved = posicionRepository.save(posicion);
        return posicionMapper.toPosicionResponseDto(posicionSaved);
    }

    public List<PosicionResponseDto> findAll() {
        return posicionRepository.findAll()
                .stream()
                .map(posicionMapper::toPosicionResponseDto)
                .collect(Collectors.toList());
    }

    public PosicionResponseDto updatePosicion(Integer id, PosicionDto dto) {
        Posicion posicion = posicionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("La posicion no existe"));

        posicion.setLatitud(dto.latitud());
        posicion.setLongitud(dto.longitud());
        posicion.setFechaHora(dto.fechaHora());

        return posicionMapper.toPosicionResponseDto(posicionRepository.save(posicion));
    }

    public void deletePosicion(Integer id) {
        posicionRepository.deleteById(id);
    }
}
