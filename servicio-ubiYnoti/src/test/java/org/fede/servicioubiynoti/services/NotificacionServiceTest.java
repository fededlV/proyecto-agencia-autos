package org.fede.servicioubiynoti.services;

import org.fede.servicioubiynoti.dto.ConfiguracionDTO;
import org.fede.servicioubiynoti.entities.Posicion;
import org.fede.servicioubiynoti.repositories.NotificacionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class NotificacionServiceTest {

    private NotificacionService notificacionService;
    private RestTemplate restTemplateMock;
    private NotificacionRepository notificacionRepositoryMock;

    @BeforeEach
    void setUp() {
        restTemplateMock = Mockito.mock(RestTemplate.class);
        notificacionRepositoryMock = Mockito.mock(NotificacionRepository.class);
        notificacionService = new NotificacionService(restTemplateMock, notificacionRepositoryMock);

        // Configuración simulada
        ConfiguracionDTO configuracion = new ConfiguracionDTO();
        configuracion.setCoordenadasAgencia(new ConfiguracionDTO.Coordenadas(-34.6, -58.4));
        configuracion.setRadioAdmitidoKm(5.0);
        configuracion.setZonasRestringidas(List.of(
                new ConfiguracionDTO.ZonaRestringida(
                        new ConfiguracionDTO.Coordenadas(-34.7, -58.5),
                        new ConfiguracionDTO.Coordenadas(-34.5, -58.3)
                )
        ));

        Mockito.when(restTemplateMock.getForObject(
                Mockito.anyString(), Mockito.eq(ConfiguracionDTO.class))
        ).thenReturn(configuracion);
    }

    @Test
    void testVehiculoFueraDelArea() {
        Posicion posicion = new Posicion(-34.8, -58.6, LocalDateTime.now());
        boolean resultado = notificacionService.verificarPosicionVehiculo(posicion);
        assertTrue(resultado, "El vehículo debería estar fuera del área.");
    }

    @Test
    void testVehiculoDentroDelArea() {
        Posicion posicion = new Posicion(-34.6, -58.4, LocalDateTime.now());
        boolean resultado = notificacionService.verificarPosicionVehiculo(posicion);
        assertFalse(resultado, "El vehículo debería estar dentro del área.");
    }

    @Test
    void testVehiculoEnZonaRestringida() {
        Posicion posicion = new Posicion(-34.6, -58.45, LocalDateTime.now());
        boolean resultado = notificacionService.verificarPosicionVehiculo(posicion);
        assertTrue(resultado, "El vehículo debería estar en un área restringida.");
    }
}
