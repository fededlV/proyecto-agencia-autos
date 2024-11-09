package org.fede.pruebas.services;

import org.fede.pruebas.dto.NotificacionDto;
import org.fede.pruebas.entities.Interesado;
import org.fede.pruebas.entities.Notificacion;
import org.fede.pruebas.repositories.InteresadoRepository;
import org.fede.pruebas.repositories.NotificacionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class NotificacionServiceTest {

    @Mock
    private NotificacionRepository notificacionRepository;

    @Mock
    private InteresadoRepository interesadoRepository;

    @Mock
    private NotificacionMapper notificacionMapper;

    @InjectMocks
    private NotificacionService notificacionService;

    private Interesado interesado;
    private NotificacionDto notificacionDto;
    private Notificacion notificacion;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        interesado = new Interesado();
        interesado.setId(1);

        notificacionDto = new NotificacionDto(1, "Mensaje de prueba", LocalDateTime.now());
        notificacion = new Notificacion(interesado, "Mensaje de prueba", LocalDateTime.now());
        notificacion.setId(1);
    }

    @Test
    void createNotificacion_Success() {
        when(interesadoRepository.findById(1)).thenReturn(Optional.of(interesado));
        when(notificacionMapper.toNotificacion(notificacionDto, interesado)).thenReturn(notificacion);
        when(notificacionRepository.save(notificacion)).thenReturn(notificacion);
        when(notificacionMapper.toNotificacionDto(notificacion)).thenReturn(notificacionDto);

        NotificacionDto result = notificacionService.createNotificacion(notificacionDto);

        assertEquals(notificacionDto, result);
        verify(notificacionRepository, times(1)).save(notificacion);
    }

    @Test
    void findAll() {
        when(notificacionRepository.findAll()).thenReturn(List.of(notificacion));
        when(notificacionMapper.toNotificacionDto(notificacion)).thenReturn(notificacionDto);

        List<NotificacionDto> result = notificacionService.findAll();

        assertEquals(1, result.size());
        assertEquals(notificacionDto, result.get(0));
    }

    @Test
    void deleteNotificacion_Success() {
        notificacionService.deleteNotificacion(1);

        verify(notificacionRepository, times(1)).deleteById(1);
    }
}
