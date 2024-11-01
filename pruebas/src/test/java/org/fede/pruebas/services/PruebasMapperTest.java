package org.fede.pruebas.services;

import org.fede.pruebas.dto.PruebaDto;
import org.fede.pruebas.dto.PruebaResponseDto;
import org.fede.pruebas.entities.Prueba;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.cglib.core.Local;
import org.springframework.security.core.parameters.P;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class PruebasMapperTest {

    private PruebasMapper pruebasMapper;

    @BeforeEach
    void setUp() {
        pruebasMapper = new PruebasMapper();
    }

    @Test
    public void testToPruebaResponseDto() {
        //Arrange
        Prueba prueba = new Prueba();
        prueba.setFechaHoraInicio(LocalDateTime.of(2024, 10, 30, 10, 0));
        prueba.setFechaHoraFin(LocalDateTime.of(2024, 10, 30, 12, 0));
        prueba.setComentarios("Prueba de manejo");

        //Act
        PruebaResponseDto responseDto = pruebasMapper.toPruebaResponseDto(prueba);

        //Assert
        assertNotNull(responseDto);
        assertEquals(prueba.getFechaHoraInicio(), responseDto.fechaHoraInicio());
        assertEquals(prueba.getFechaHoraFin(), responseDto.fechaHoraFin());
        assertEquals(prueba.getComentarios(), responseDto.comentario());
    }

    @Test
    public void testToPruebaResponseDtoWithNullPrueba() {
        //Arrange and act
        var msg = assertThrows(NullPointerException.class, () -> pruebasMapper.toPruebaResponseDto(null));

        // Asserrt
        assertEquals("La prueba no deberia ser nula", msg.getMessage());
    }

    @Test
    public void testToPrueba() {
        //Arrange
        Integer idVehiculo = 1;
        Integer idInteresado = 1;
        Integer idEmpleado = 1;
        LocalDateTime fechaHoraInicio = LocalDateTime.of(2024, 10, 30, 10, 0);
        LocalDateTime fechaHoraFin = LocalDateTime.of(2024, 10, 30, 12, 0);
        String comentario = "Prueba de manejo";
        PruebaDto pruebaDto = new PruebaDto(
                idVehiculo,
                idInteresado,
                idEmpleado,
                fechaHoraFin,
                fechaHoraInicio,
                comentario
        );

        //Act
        Prueba prueba = pruebasMapper.toPrueba(pruebaDto);

        //Assert
        assertNotNull(prueba);
        assertEquals(pruebaDto.fechaHoraInicio(), prueba.getFechaHoraInicio());
        assertEquals(pruebaDto.fechaHoraFin(), prueba.getFechaHoraFin());
        assertEquals(pruebaDto.comentario(), prueba.getComentarios());
    }

    @Test
    public void testToPruebaWithNullDto() {
        //Arrange
        PruebaDto pruebaDto = null;

        // Act & Assert
        assertThrows(NullPointerException.class, () -> pruebasMapper.toPrueba(pruebaDto));
    }
}