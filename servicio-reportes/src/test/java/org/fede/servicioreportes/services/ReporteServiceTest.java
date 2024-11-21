package org.fede.servicioreportes.services;

import org.fede.servicioreportes.dto.IncidenteDTO;
import org.fede.servicioreportes.dto.PruebaResponseDTO;
import org.fede.servicioreportes.entities.*;
import org.fede.servicioreportes.model.Coordenada;
import org.fede.servicioreportes.model.ZonaRestringida;
import org.fede.servicioreportes.repositories.PosicionRepository;
import org.fede.servicioreportes.repositories.PruebaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReporteServiceTest {

    @Mock
    private ConfiguracionService configuracionService;

    @Mock
    private PosicionRepository posicionRepository;

    @Mock
    private PruebaRepository pruebaRepository;

    @Mock
    private PruebasMapper mapper;

    @InjectMocks
    private ReporteService reporteService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void calcularKilometrosRecorridos_DeberiaRetornarDistanciaCorrecta() {
        //Datos de entrada
        Integer vehiculoId = 1;
        Vehiculo vehiculo = new Vehiculo();
        vehiculo.setId(vehiculoId);
        LocalDateTime fechaInicio = LocalDateTime.of(2024, 1,1,0,0);
        LocalDateTime fechaFin = LocalDateTime.of(2024, 1,2,0,0);

        List<Posicion> posiciones = List.of(
                new Posicion(fechaInicio.plusHours(1), 10.0, 10.0, vehiculo),
                new Posicion(fechaInicio.plusHours(2), 11.0, 11.0, vehiculo),
                new Posicion(fechaInicio.plusHours(3), 12.0, 12.0, vehiculo)
        );

        //Mock del repositorio
        when(posicionRepository.findPosicionByVehiculoAndPeriodo(vehiculoId, fechaInicio, fechaFin)).thenReturn(posiciones);

        // Llamada al metodo
        double resultado = reporteService.calcularKilometrosRecorridos(vehiculoId, fechaInicio, fechaFin);

        //Validaciones
        assertTrue(resultado > 0, "La distancia total debe ser mayor a 0.");
        verify(posicionRepository, times(1)).findPosicionByVehiculoAndPeriodo(vehiculoId, fechaInicio, fechaFin);
    }

    //Test pruebas por vehiculo
    @Test
    void obtenerPruebasPorVehiculo_DeberiaRetornarUnaListaDePruebaResponseDTO() {
        //Datos de entrada
        Integer vehiculoId = 1;
        Vehiculo vehiculo = new Vehiculo();
        vehiculo.setId(vehiculoId);
        Cliente interesado = new Cliente();
        Empleado empleado = new Empleado();
        Prueba prueba = new Prueba(LocalDateTime.now(),
                "Prueba De manejo",
                LocalDateTime.now().plusHours(1),
                vehiculo,
                interesado,
                empleado);
        Prueba prueba1 = new Prueba(LocalDateTime.now(),
                        "Prueba De manejo",
                        LocalDateTime.now().plusHours(2),
                        vehiculo,
                        interesado,
                        empleado);

        //Pruebas simuladas
        List<Prueba> pruebas = new ArrayList<>();
        pruebas.add(prueba1);
        pruebas.add(prueba);

        PruebaResponseDTO dto1 = mapper.toPruebaResponseDto(prueba);
        PruebaResponseDTO dto2 = mapper.toPruebaResponseDto(prueba1);

        when(pruebaRepository.findPruebaByVehiculo(vehiculoId)).thenReturn(pruebas);

        //Llamada al metodo
        List<PruebaResponseDTO> resultado = reporteService.obtenerPruebasPorVehiculo(vehiculoId);

        //Validaciones
        assertEquals(2, resultado.size(), "El numero de pruebas debe coincidir con las esperadas.");
        assertEquals(dto1, resultado.get(0), "El primer DTO debe coincidir con la primer prueba");
        assertEquals(dto2, resultado.get(1), "El segundo DTO debe coincidir con la segunda prueba");
        verify(pruebaRepository, times(1)).findPruebaByVehiculo(vehiculoId);
    }

    @Test
    void generarReporteDeIncidentes_DeberiaRetornarIncidentesCorrectos() {
        // Mock datos de configuración
        when(configuracionService.obtenerUbicacionAgencia()).thenReturn(new Coordenada(42.508867, 1.534713));
        when(configuracionService.obtenerRadioPermitido()).thenReturn(5.0);
        when(configuracionService.obtenerZonasRestringida()).thenReturn(List.of(
                new ZonaRestringida(new Coordenada(42.510006, 1.536654), new Coordenada(42.508744, 1.538775))
        ));

        // Mock datos de prueba y posición
        int id = 1;
        Vehiculo vehiculo = new Vehiculo();
        vehiculo.setId(id);
        Cliente cliente = new Cliente();
        cliente.setId(id);
        Empleado empleado = new Empleado();
        empleado.setLegajo(id);

        Posicion posicion = new Posicion(LocalDateTime.now(), 42.511, 1.537, vehiculo);
        posicion.setId(id);
        assertNotNull(posicion.getLatitud(), "La latitud de la posición no debe ser nula");
        assertNotNull(posicion.getLongitud(), "La longitud de la posición no debe ser nula");

        Prueba prueba = new Prueba(LocalDateTime.now().minusDays(1), "Comentario de prueba",  LocalDateTime.now(), vehiculo, cliente, empleado);
        prueba.setId(id);


        // Mock de repositorios
        when(pruebaRepository.findAll()).thenReturn(List.of(prueba)); // Devolver la prueba en la base de datos simulada
        when(posicionRepository.findByVehiculoId(vehiculo.getId())).thenReturn(List.of(posicion)); // Devolver la posición del vehículo

        // Ejecutar el método
        List<IncidenteDTO> incidentes = reporteService.generarReporteIncidentes();

        // Validar resultados
        assertEquals(1, incidentes.size(), "Se espera 1 incidente.");
        assertEquals("Ingresó a una zona peligrosa", incidentes.get(0).descripcion(), "La descripción del incidente no es la esperada.");
    }
}