package org.fede.pruebas.services;

import org.fede.pruebas.dto.PruebaDto;
import org.fede.pruebas.dto.PruebaResponseDto;
import org.fede.pruebas.entities.Prueba;
import org.fede.pruebas.repositories.PruebaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PruebasServicesTest {

    @Mock
    private PruebaRepository repository;

    @Mock
    private PruebasMapper mapper;

    @InjectMocks
    private PruebasServices pruebasServices;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreatePrueba_SuccessfulCreation() {
        // 1. Crear un DTO de prueba con datos de inicio y fin de la prueba
        PruebaDto dto = new PruebaDto(
                1,
                1,
                1,
                LocalDateTime.now(),
                LocalDateTime.now().plusHours(1),
                "Prueba de manejo"
        );

        // 2. Crear una instancia de la entidad Prueba, inicializándola con los mismos datos del DTO
        Prueba prueba = new Prueba();
        prueba.setFechaHoraInicio(dto.fechaHoraInicio());
        prueba.setFechaHoraFin(dto.fechaHoraFin());

        // 3. Crear el DTO de respuesta, que representa cómo se devolverán los datos tras guardar
        PruebaResponseDto responseDto = new PruebaResponseDto(
                LocalDateTime.now(),
                LocalDateTime.now().plusHours(1),
                "Prueba de manejo"
        );

        // 4. Configurar el mock del mapper para que convierta el DTO en la entidad
        when(mapper.toPrueba(dto)).thenReturn(prueba);

        // 5. Configurar el mock del repositorio para que indique que el vehículo no está en prueba
        when(repository.existsByVehiculoAndFechaHoraInicioLessThanEqualAndFechaHoraFinGreaterThanEqual(
                prueba.getVehiculo(), prueba.getFechaHoraInicio(), prueba.getFechaHoraFin())).thenReturn(false);

        // 6. Configurar el mock del repositorio para que guarde la entidad Prueba y la devuelva
        when(repository.save(prueba)).thenReturn(prueba);

        // 7. Configurar el mock del mapper para que convierta la entidad guardada en el DTO de respuesta
        when(mapper.toPruebaResponseDto(prueba)).thenReturn(responseDto);

        // 8. Ejecutar el método create y verificar el resultado
        PruebaResponseDto result = pruebasServices.create(dto);

        // 9. Aserciones para verificar que el resultado no es nulo y que el DTO de respuesta es correcto
        assertNotNull(result);
        assertEquals(responseDto, result);

        // 10. Verificar que el repositorio fue invocado una vez para guardar la prueba
        verify(repository, times(1)).save(prueba);
    }

    @Test
    void testCreatePrueba_VehiculoEnPrueba_ExceptionThrown() {
        // 1. Crear un DTO de prueba
        Prueba prueba = new Prueba();
        PruebaDto dto = new PruebaDto(
                1,
                1,
                1,
                LocalDateTime.now(),
                LocalDateTime.now().plusHours(1),
                "Prueba de manejo"
        );

        // 2. Configurar el mock del mapper para convertir el DTO en la entidad Prueba
        when(mapper.toPrueba(dto)).thenReturn(prueba);

        // 3. Configurar el mock del repositorio para que indique que el vehículo ya está en prueba
        when(repository.existsByVehiculoAndFechaHoraInicioLessThanEqualAndFechaHoraFinGreaterThanEqual(
                any(), any(), any())).thenReturn(true);

        // 4. Ejecutar el método y verificar que lanza una excepción debido a la colisión de fechas
        Exception exception = assertThrows(RuntimeException.class, () -> {
            pruebasServices.create(dto);
        });

        // 5. Verificar el mensaje de excepción para asegurarse de que es el esperado
        assertEquals("El vehiculo ya esta siendo probado en este momento", exception.getMessage());

        // 6. Verificar que no se guardó nada en el repositorio (el método save no debería ser invocado)
        verify(repository, never()).save(prueba);
    }

    @Test
    void testFindAll_ReturnsAllPruebas() {
        // 1. Crear instancias de Prueba y sus correspondientes DTOs de respuesta
        Prueba prueba1 = new Prueba();
        Prueba prueba2 = new Prueba();
        PruebaResponseDto dto1 = new PruebaResponseDto(
                LocalDateTime.now(),
                null,
                null
        );
        PruebaResponseDto dto2 = new PruebaResponseDto(
                LocalDateTime.now(),
                null,
                null
        );
        prueba1.setFechaHoraInicio(LocalDateTime.now());
        prueba2.setFechaHoraInicio(LocalDateTime.now().plusDays(1));

        // 2. Configurar el mock del repositorio para que devuelva una lista de entidades Prueba
        when(repository.findAll()).thenReturn(Arrays.asList(prueba1, prueba2));

        // 3. Configurar el mock del mapper para convertir cada entidad Prueba en su respectivo DTO de respuesta
        when(mapper.toPruebaResponseDto(prueba1)).thenReturn(dto1);
        when(mapper.toPruebaResponseDto(prueba2)).thenReturn(dto2);

        // 4. Ejecutar el método findAll y capturar el resultado
        List<PruebaResponseDto> result = pruebasServices.findAll();

        // 5. Verificar que el resultado contiene el número esperado de elementos y que coinciden con los DTOs esperados
        assertEquals(2, result.size());
        assertEquals(dto1, result.get(0));
        assertEquals(dto2, result.get(1));

        // 6. Verificar que el repositorio fue invocado una vez para obtener todas las pruebas
        verify(repository, times(1)).findAll();
    }

    @Test
    void testListarPruebasEnCurso_ReturnsPruebasEnCurso() {
    // 1. Crear una fecha y hora para la prueba
    LocalDateTime fechaHora = LocalDateTime.now();

    // 2. Crear instancias de Prueba y sus correspondientes DTOs de respuesta
    Prueba prueba1 = new Prueba();
    Prueba prueba2 = new Prueba();
    PruebaResponseDto dto1 = new PruebaResponseDto(
            LocalDateTime.now(),
            null,
            null
    );
    PruebaResponseDto dto2 = new PruebaResponseDto(
            LocalDateTime.now(),
            null,
            null
    );
    prueba1.setFechaHoraInicio(LocalDateTime.now());
    prueba2.setFechaHoraInicio(LocalDateTime.now().plusDays(1));

    // 3. Configurar el mock del repositorio para que devuelva una lista de entidades Prueba en curso
    when(repository.findByPruebasEnCurso(fechaHora)).thenReturn(Arrays.asList(prueba1, prueba2));

    // 4. Configurar el mock del mapper para convertir cada entidad Prueba en su respectivo DTO de respuesta
    when(mapper.toPruebaResponseDto(prueba1)).thenReturn(dto1);
    when(mapper.toPruebaResponseDto(prueba2)).thenReturn(dto2);

    // 5. Ejecutar el método listarPruebasEnCurso y capturar el resultado
    List<PruebaResponseDto> result = pruebasServices.listarPruebasEnCurso(fechaHora);

    // 6. Verificar que el resultado contiene el número esperado de elementos y que coinciden con los DTOs esperados
    assertEquals(2, result.size());
    assertEquals(dto1, result.get(0));
    assertEquals(dto2, result.get(1));

    // 7. Verificar que el repositorio fue invocado una vez para obtener las pruebas en curso
    verify(repository, times(1)).findByPruebasEnCurso(fechaHora);
}
}