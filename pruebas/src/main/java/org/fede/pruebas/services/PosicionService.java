package org.fede.pruebas.services;

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

    public void createPosicion(PosicionDto dto){
        Posicion posicion = posicionMapper.toPosicion(dto);
        posicionRepository.save(posicion);
    }

    public List<PosicionResponseDto> findAll() {
        return posicionRepository.findAll()
                .stream()
                .map(posicionMapper::toPosicionResponseDto)
                .collect(Collectors.toList());
    }
}
