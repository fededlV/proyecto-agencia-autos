package org.fede.pruebas.services;

import jakarta.persistence.EntityNotFoundException;
import org.fede.pruebas.dto.NotificacionDto;
import org.fede.pruebas.entities.Interesado;
import org.fede.pruebas.entities.Notificacion;
import org.fede.pruebas.repositories.InteresadoRepository;
import org.fede.pruebas.repositories.NotificacionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificacionService {

    private final NotificacionRepository notificacionRepository;
    private final InteresadoRepository interesadoRepository;
    private final NotificacionMapper notificacionMapper;

    public NotificacionService(NotificacionRepository notificacionRepository, InteresadoRepository interesadoRepository, NotificacionMapper notificacionMapper) {
        this.notificacionRepository = notificacionRepository;
        this.interesadoRepository = interesadoRepository;
        this.notificacionMapper = notificacionMapper;
    }

    public NotificacionDto createNotificacion(NotificacionDto dto) {
        Interesado interesado = interesadoRepository.findById(dto.interesadoId())
                .orElseThrow(() -> new EntityNotFoundException("Interesado no encontrado con id: " + dto.interesadoId()));
        Notificacion notificacion = notificacionMapper.toNotificacion(dto, interesado);
        Notificacion savedNotificacion = notificacionRepository.save(notificacion);
        return notificacionMapper.toNotificacionDto(savedNotificacion);
    }

    public List<NotificacionDto> findAll() {
        return notificacionRepository.findAll()
                .stream()
                .map(notificacionMapper::toNotificacionDto)
                .collect(Collectors.toList());
    }

    public void deleteNotificacion(Integer id) {
        notificacionRepository.deleteById(id);
    }
}
