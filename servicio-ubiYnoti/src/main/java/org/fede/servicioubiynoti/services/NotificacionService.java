package org.fede.servicioubiynoti.services;

import org.fede.servicioubiynoti.dto.ConfiguracionDTO;
import org.fede.servicioubiynoti.entities.Notificacion;
import org.fede.servicioubiynoti.exceptions.VehiculoNoEncontrado;
import org.fede.servicioubiynoti.exceptions.EmpleadoNoEncontrado;
import org.fede.servicioubiynoti.repositories.NotificacionRepository;
import org.fede.servicioubiynoti.repositories.PruebaRepository;
import org.fede.servicioubiynoti.repositories.VehiculoRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class NotificacionService {

    private final RestTemplate restTemplate;
    private final NotificacionRepository notificacionRepository;
    private final PruebaRepository pruebaRepository; // Nuevo repositorio
    private final VehiculoRepository vehiculoRepository;
    public NotificacionService(RestTemplate restTemplate, NotificacionRepository notificacionRepository, PruebaRepository pruebaRepository, VehiculoRepository vehiculoRepository) {
        this.restTemplate = restTemplate;
        this.notificacionRepository = notificacionRepository;
        this.pruebaRepository = pruebaRepository; // Inicializar el repositorio
        this.vehiculoRepository = vehiculoRepository;
    }

    public boolean evaluarPosicionVehiculo(Integer id_vehiculo, LocalDateTime fechaHora, Double latitud, Double longitud) {
        ConfiguracionDTO configuracion = obtenerConfiguracion();
        return procesarPosicion(id_vehiculo, fechaHora, latitud, longitud, configuracion);
    }

    private ConfiguracionDTO obtenerConfiguracion() {
        return restTemplate.getForObject(
                "https://labsys.frc.utn.edu.ar/apps-disponibilizadas/backend/api/v1/configuracion/",
                ConfiguracionDTO.class
        );
    }

    private boolean procesarPosicion(Integer id_vehiculo, LocalDateTime fechaHora, Double latitud, Double longitud, ConfiguracionDTO configuracion) {
        double distancia = calcularDistancia(
                latitud, longitud,
                configuracion.getCoordenadasAgencia().getLat(),
                configuracion.getCoordenadasAgencia().getLon()
        );

        if (distancia > configuracion.getRadioAdmitidoKm() || enZonaRestringida(latitud, longitud, configuracion)) {

            Optional<Integer> vehiculo = vehiculoRepository.findIdByVehiculoId(id_vehiculo);
            if (vehiculo.isEmpty()) {
                throw new VehiculoNoEncontrado("No existe un vehículo en prueba con el ID proporcionado.");
            }

            Optional<Integer> legajo = pruebaRepository.findLegajoEmpleadoPorPruebaActiva(id_vehiculo, fechaHora);
            if (legajo.isEmpty()) {
                throw new EmpleadoNoEncontrado("No se encontró un empleado asignado a la prueba activa.");
            }

            Integer legajoEmpleado = legajo.get();

            // Crear y guardar la notificación
            Notificacion notificacion = new Notificacion();
            notificacion.setMensaje("El vehículo ha excedido los límites permitidos.");
            notificacion.setFechaEnvio(LocalDateTime.now());
            notificacion.setEsIncidente(true);
            notificacion.setLegajo(legajoEmpleado);

            notificacionRepository.save(notificacion);
            return true;
        }
        return false;
    }

    private boolean enZonaRestringida(Double latitud, Double longitud, ConfiguracionDTO configuracion) {
        return configuracion.getZonasRestringidas().stream().anyMatch(zona -> {
            double latNoroeste = zona.getNoroeste().getLat();
            double lonNoroeste = zona.getNoroeste().getLon();
            double latSureste = zona.getSureste().getLat();
            double lonSureste = zona.getSureste().getLon();

            // Verificar si el vehículo está dentro de los límites de la zona
            return latitud >= latSureste && latitud <= latNoroeste &&
                    longitud >= lonNoroeste && longitud <= lonSureste;
        });
    }

    private double calcularDistancia(Double lat1, Double lon1, Double lat2, Double lon2) {
        double lat1EnKm = lat1 * 111;
        double lon1EnKm = lon1 * 111 * Math.cos(Math.toRadians(lat1));
        double lat2EnKm = lat2 * 111;
        double lon2EnKm = lon2 * 111 * Math.cos(Math.toRadians(lat2));

        double deltaX = lon2EnKm - lon1EnKm;
        double deltaY = lat2EnKm - lat1EnKm;
        return Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));
    }
}
