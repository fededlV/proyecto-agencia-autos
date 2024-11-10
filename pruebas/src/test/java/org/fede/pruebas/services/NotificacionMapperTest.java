package org.fede.pruebas.services;

import org.fede.pruebas.dto.NotificacionDto;
import org.fede.pruebas.entities.Empleado;
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
        Empleado empleado = new Empleado();
        empleado.setLegajo(1);
        Notificacion notificacion = new Notificacion();
        notificacion.setEmpleado(empleado);
        notificacion.setMensaje("Mensaje de prueba");
        notificacion.setFechaEnvio(LocalDateTime.of(2024, 10, 30, 10, 0));
        notificacion.setEsIncidente(false);

        NotificacionDto notificacionDto = notificacionMapper.toNotificacionDto(notificacion);

        assertNotNull(notificacionDto);
        assertEquals(notificacion.getEmpleado().getLegajo(), notificacionDto.empleadoId());
        assertEquals(notificacion.getMensaje(), notificacionDto.mensaje());
        assertEquals(notificacion.getFechaEnvio(), notificacionDto.fechaEnvio());
        assertEquals(notificacion.getEsIncidente(), notificacionDto.esIncidente());
    }

    @Test
    public void testToNotificacionDtoWithNullNotificacion() {
        var exception = assertThrows(NullPointerException.class, () -> notificacionMapper.toNotificacionDto(null));
        assertEquals("La notificación no debería ser nula", exception.getMessage());
    }

    @Test
    public void testToNotificacion() {
        Integer empleadoId = 1;
        String mensaje = "Mensaje de prueba";
        LocalDateTime fechaEnvio = LocalDateTime.of(2024, 10, 30, 10, 0);
        Boolean esIncidente = true;
        NotificacionDto notificacionDto = new NotificacionDto(empleadoId, mensaje, fechaEnvio, esIncidente);

        Empleado empleado = new Empleado();
        empleado.setLegajo(empleadoId);

        Notificacion notificacion = notificacionMapper.toNotificacion(notificacionDto, empleado);

        assertNotNull(notificacion);
        assertEquals(empleado, notificacion.getEmpleado());
        assertEquals(notificacionDto.mensaje(), notificacion.getMensaje());
        assertEquals(notificacionDto.fechaEnvio(), notificacion.getFechaEnvio());
        assertEquals(notificacionDto.esIncidente(), notificacion.getEsIncidente());
    }

    @Test
    public void testToNotificacionWithNullDto() {
        NotificacionDto notificacionDto = null;
        Empleado empleado = new Empleado();

        assertThrows(NullPointerException.class, () -> notificacionMapper.toNotificacion(notificacionDto, empleado));
    }

    @Test
    public void testToNotificacionWithNullEmpleado() {
        NotificacionDto notificacionDto = new NotificacionDto(1, "Mensaje de prueba", LocalDateTime.now(), false);

        assertThrows(NullPointerException.class, () -> notificacionMapper.toNotificacion(notificacionDto, null));
    }
}
