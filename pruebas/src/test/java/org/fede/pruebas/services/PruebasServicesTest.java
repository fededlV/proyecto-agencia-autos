package org.fede.pruebas.services;

import org.fede.pruebas.dto.PruebaDto;
import org.fede.pruebas.dto.PruebaResponseDto;
import org.fede.pruebas.entities.Empleado;
import org.fede.pruebas.entities.Interesado;
import org.fede.pruebas.entities.Prueba;
import org.fede.pruebas.entities.Vehiculo;
import org.fede.pruebas.repositories.EmpleadoRepository;
import org.fede.pruebas.repositories.InteresadoRepository;
import org.fede.pruebas.repositories.PruebaRepository;
import org.fede.pruebas.repositories.VehiculoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PruebasServicesTest {

    @Mock
    private PruebaRepository repository;

    @Mock
    private PruebasMapper mapper;

    @Mock
    private VehiculoRepository vehiculoRepository;

    @Mock
    private EmpleadoRepository empleadoRepository;

    @Mock
    private InteresadoRepository interesadoRepository;

    @Mock
    private InteresadoService interesadoService;

    @InjectMocks
    private PruebasServices pruebasServices;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreatePrueba_SuccessfulCreation() {
        // Crear el DTO de prueba
        PruebaDto dto = new PruebaDto(
                1,
                1,
                1,
                LocalDateTime.now(),
                LocalDateTime.now().plusHours(1),
                "Prueba de manejo"
        );

        // Crear instancias de Vehiculo, Empleado, e Interesado
        Vehiculo vehiculo = new Vehiculo();
        Empleado empleado = new Empleado();
        Interesado interesado = new Interesado();

        // Configurar el comportamiento de los mocks
        when(vehiculoRepository.findById(dto.idVehiculo())).thenReturn(Optional.of(vehiculo));
        when(empleadoRepository.findById(dto.idEmpleado())).thenReturn(Optional.of(empleado));
        when(interesadoRepository.findById(dto.idInteresado())).thenReturn(Optional.of(interesado));

        // Configurar otros mocks necesarios
        Prueba prueba = new Prueba();
        when(mapper.toPrueba(dto)).thenReturn(prueba);
        when(repository.existsByVehiculoAndFechaHoraInicioLessThanEqualAndFechaHoraFinGreaterThanEqual(
                prueba.getVehiculo(), prueba.getFechaHoraInicio(), prueba.getFechaHoraFin())).thenReturn(false);

        PruebaResponseDto responseDto = new PruebaResponseDto(
                LocalDateTime.now(),
                LocalDateTime.now().plusHours(1),
                "Prueba de manejo"
        );
        when(mapper.toPruebaResponseDto(prueba)).thenReturn(responseDto);
        when(repository.save(prueba)).thenReturn(prueba);

        // Ejecutar el metodo create y verificar el resultado
        PruebaResponseDto result = pruebasServices.create(dto);

        // Verificar que el resultado no es nulo y es igual a responseDto
        assertNotNull(result);
        assertEquals(responseDto, result);
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

        // 4. Ejecutar el metodo y verificar que lanza una excepción debido a la colisión de fechas
        Exception exception = assertThrows(RuntimeException.class, () -> {
            pruebasServices.create(dto);
        });

        // 5. Verificar el mensaje de excepción para asegurarse de que es el esperado
        assertEquals("El vehiculo ya esta siendo probado en este momento", exception.getMessage());

        // 6. Verificar que no se guardó nada en el repositorio (el metodo save no debería ser invocado)
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

        // 4. Ejecutar el metodo findAll y capturar el resultado
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