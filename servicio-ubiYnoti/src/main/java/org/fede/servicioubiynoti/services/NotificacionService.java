package org.fede.servicioubiynoti.services;

import org.fede.servicioubiynoti.dto.ConfiguracionDTO;
import org.fede.servicioubiynoti.entities.Notificacion;
import org.fede.servicioubiynoti.entities.Posicion;
import org.fede.servicioubiynoti.repositories.NotificacionRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

@Service
public class NotificacionService {

    private final RestTemplate restTemplate;
    private final NotificacionRepository notificacionRepository;

    public NotificacionService(RestTemplate restTemplate, NotificacionRepository notificacionRepository) {
        this.restTemplate = restTemplate;
        this.notificacionRepository = notificacionRepository;
    }

    public boolean evaluarPosicionVehiculo(Double latitud, Double longitud) {
        ConfiguracionDTO configuracion = obtenerConfiguracion();
        return procesarPosicion(latitud, longitud, configuracion);
    }

    public boolean verificarPosicionVehiculo(Posicion posicion) {
        ConfiguracionDTO configuracion = obtenerConfiguracion();
        return procesarPosicion(posicion.getLatitud(), posicion.getLongitud(), configuracion);
    }

    private ConfiguracionDTO obtenerConfiguracion() {
        return restTemplate.getForObject(
                "https://labsys.frc.utn.edu.ar/apps-disponibilizadas/backend/api/v1/configuracion/",
                ConfiguracionDTO.class
        );
    }

    private boolean procesarPosicion(Double latitud, Double longitud, ConfiguracionDTO configuracion) {
        double distancia = calcularDistancia(
                latitud, longitud,
                configuracion.getCoordenadasAgencia().getLat(),
                configuracion.getCoordenadasAgencia().getLon()
        );

        if (distancia > configuracion.getRadioAdmitidoKm() || enZonaRestringida(latitud, longitud, configuracion)) {
            Notificacion notificacion = new Notificacion();
            notificacion.setMensaje("El vehículo ha excedido los límites permitidos.");
            notificacion.setFechaEnvio(LocalDateTime.now());
            notificacion.setEsIncidente(true);
            notificacionRepository.save(notificacion);
            return true;
        }
        return false;
    }

    private boolean enZonaRestringida(Double latitud, Double longitud, ConfiguracionDTO configuracion) {
        return configuracion.getZonasRestringidas().stream().anyMatch(zona ->
                latitud <= zona.getNoroeste().getLat() && latitud >= zona.getSureste().getLat() &&
                        longitud >= zona.getNoroeste().getLon() && longitud <= zona.getSureste().getLon()
        );
    }

    private double calcularDistancia(Double lat1, Double lon1, Double lat2, Double lon2) {
        return Math.sqrt(Math.pow(lat2 - lat1, 2) + Math.pow(lon2 - lon1, 2)) * 111;
    }
}
