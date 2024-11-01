package org.fede.pruebas.services;

import jakarta.persistence.EntityNotFoundException;
import org.fede.pruebas.dto.InteresadoDto;
import org.fede.pruebas.entities.Interesado;
import org.fede.pruebas.repositories.InteresadoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InteresadoService {

    private final InteresadoRepository interesadoRepository;
    private final InteresadoMapper interesadoMapper;

    public InteresadoService(InteresadoRepository interesadoRepository, InteresadoMapper interesadoMapper) {
        this.interesadoRepository = interesadoRepository;
        this.interesadoMapper = interesadoMapper;
    }

    public void createInteresado(InteresadoDto interesadoDto) {
        var interesado = interesadoMapper.toInteresado(interesadoDto);
        interesadoRepository.save(interesado);
    }


    public Interesado validarInteresado(Integer idInteresado) {
        Interesado interesado = interesadoRepository.findById(idInteresado)
                .orElseThrow(() -> new EntityNotFoundException("Interesado no encontrado"));

        // Validar que la licencia no este vencida
        if(interesado.getFechaVenLicencia().isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("la licencia del interesado esta vencida");
        }

        // Validar que el interesado no este restringido
        if(interesado.getRestringido()) {
            throw new IllegalStateException("El interesado esta restringido para probar vehiculos");
        }

        return interesado;
    }

    public List<InteresadoDto> findAll() {
        return interesadoRepository.findAll()
                .stream()
                .map(interesadoMapper::toInteresadoDto)
                .collect(Collectors.toList());
    }
}
