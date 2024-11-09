package org.fede.pruebas.services;

import org.fede.pruebas.dto.NotificacionDto;
import org.fede.pruebas.entities.Interesado;
import org.fede.pruebas.entities.Notificacion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class NotificacionMapperTest {

    private NotificacionMapper notificacionMapper;

    @BeforeEach
    void setUp() {
        notificacionMapper = new NotificacionMapper();
    }

    @Test
    public void testToNotificacionDto() {
        Interesado interesado = new Interesado();
        interesado.setId(1);
        Notificacion notificacion = new Notificacion();
        notificacion.setInteresado(interesado);
        notificacion.setMensaje("Mensaje de prueba");
        notificacion.setFechaEnvio(LocalDateTime.of(2024, 10, 30, 10, 0));

        NotificacionDto notificacionDto = notificacionMapper.toNotificacionDto(notificacion);

        assertNotNull(notificacionDto);
        assertEquals(notificacion.getInteresado().getId(), notificacionDto.interesadoId());
        assertEquals(notificacion.getMensaje(), notificacionDto.mensaje());
        assertEquals(notificacion.getFechaEnvio(), notificacionDto.fechaEnvio());
    }

    @Test
    public void testToNotificacionDtoWithNullNotificacion() {
        var exception = assertThrows(NullPointerException.class, () -> notificacionMapper.toNotificacionDto(null));
        assertEquals("La notificación no debería ser nula", exception.getMessage());
    }

    @Test
    public void testToNotificacion() {
        Integer interesadoId = 1;
        String mensaje = "Mensaje de prueba";
        LocalDateTime fechaEnvio = LocalDateTime.of(2024, 10, 30, 10, 0);
        NotificacionDto notificacionDto = new NotificacionDto(interesadoId, mensaje, fechaEnvio);

        Interesado interesado = new Interesado();
        interesado.setId(interesadoId);

        Notificacion notificacion = notificacionMapper.toNotificacion(notificacionDto, interesado);

        assertNotNull(notificacion);
        assertEquals(interesado, notificacion.getInteresado());
        assertEquals(notificacionDto.mensaje(), notificacion.getMensaje());
        assertEquals(notificacionDto.fechaEnvio(), notificacion.getFechaEnvio());
    }

    @Test
    public void testToNotificacionWithNullDto() {
        NotificacionDto notificacionDto = null;
        Interesado interesado = new Interesado();

        assertThrows(NullPointerException.class, () -> notificacionMapper.toNotificacion(notificacionDto, interesado));
    }

    @Test
    public void testToNotificacionWithNullInteresado() {
        NotificacionDto notificacionDto = new NotificacionDto(1, "Mensaje de prueba", LocalDateTime.now());

        assertThrows(NullPointerException.class, () -> notificacionMapper.toNotificacion(notificacionDto, null));
    }
}
