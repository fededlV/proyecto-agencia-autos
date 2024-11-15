package org.fede.servicioubiynoti.services;

import jakarta.persistence.EntityNotFoundException;
import org.fede.pruebas.dto.NotificacionDto;
import org.fede.pruebas.entities.Empleado;
import org.fede.pruebas.entities.Notificacion;
import org.fede.pruebas.repositories.EmpleadoRepository;
import org.fede.pruebas.repositories.NotificacionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificacionService {

    private final NotificacionRepository notificacionRepository;
    private final EmpleadoRepository empleadoRepository;
    private final NotificacionMapper notificacionMapper;

    public NotificacionService(NotificacionRepository notificacionRepository, EmpleadoRepository empleadoRepository, NotificacionMapper notificacionMapper) {
        this.notificacionRepository = notificacionRepository;
        this.empleadoRepository = empleadoRepository;
        this.notificacionMapper = notificacionMapper;
    }

    public NotificacionDto createNotificacion(NotificacionDto dto) {
        Empleado empleado = empleadoRepository.findById(dto.empleadoId())
                .orElseThrow(() -> new EntityNotFoundException("Empleado no encontrado con id: " + dto.empleadoId()));
        Notificacion notificacion = notificacionMapper.toNotificacion(dto, empleado);
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
