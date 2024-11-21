package org.fede.servicioubiynoti.services;

import org.fede.servicioubiynoti.dto.ConfiguracionDTO;
import org.fede.servicioubiynoti.entities.Notificacion;
import org.fede.servicioubiynoti.entities.Posicion;
import org.fede.servicioubiynoti.entities.Empleado;
import org.fede.servicioubiynoti.repositories.NotificacionRepository;
import org.fede.servicioubiynoti.repositories.EmpleadoRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

@Service
public class NotificacionService {

    private final RestTemplate restTemplate;
    private final NotificacionRepository notificacionRepository;
    private final EmpleadoRepository empleadoRepository; // Nuevo repositorio

    public NotificacionService(RestTemplate restTemplate, NotificacionRepository notificacionRepository, EmpleadoRepository empleadoRepository) {
        this.restTemplate = restTemplate;
        this.notificacionRepository = notificacionRepository;
        this.empleadoRepository = empleadoRepository; // Inicializar repositorio
    }

    public boolean evaluarPosicionVehiculo(Double latitud, Double longitud, Integer empleadoLegajo) {
        ConfiguracionDTO configuracion = obtenerConfiguracion();
        return procesarPosicion(latitud, longitud, empleadoLegajo, configuracion);
    }

    public boolean verificarPosicionVehiculo(Posicion posicion, Integer empleadoLegajo) {
        ConfiguracionDTO configuracion = obtenerConfiguracion();
        return procesarPosicion(posicion.getLatitud(), posicion.getLongitud(), empleadoLegajo, configuracion);
    }

    private ConfiguracionDTO obtenerConfiguracion() {
        return restTemplate.getForObject(
                "https://labsys.frc.utn.edu.ar/apps-disponibilizadas/backend/api/v1/configuracion/",
                ConfiguracionDTO.class
        );
    }

    private boolean procesarPosicion(Double latitud, Double longitud, Integer empleadoLegajo, ConfiguracionDTO configuracion) {
        double distancia = calcularDistancia(
                latitud, longitud,
                configuracion.getCoordenadasAgencia().getLat(),
                configuracion.getCoordenadasAgencia().getLon()
        );

        if (distancia > configuracion.getRadioAdmitidoKm() || enZonaRestringida(latitud, longitud, configuracion)) {
            Empleado empleado = empleadoRepository.findByLegajo(empleadoLegajo)
                    .orElseThrow(() -> new IllegalArgumentException("No se encontró un empleado con el legajo: " + empleadoLegajo));

            Notificacion notificacion = new Notificacion();
            notificacion.setEmpleado(empleado); // Asociar empleado a la notificación
            notificacion.setMensaje("El vehículo ha excedido los límites permitidos.");
            notificacion.setFechaEnvio(LocalDateTime.now());
            notificacion.setEsIncidente(true);
            notificacionRepository.save(notificacion);
            return true;
        }
        return false;
    }

    private boolean enZonaRestringida(Double latitud, Double longitud, ConfiguracionDTO configuracion) {
        // Depuración: Mostrar las coordenadas del vehículo
        System.out.println("Evaluando zona restringida para las coordenadas del vehículo:");
        System.out.println("Latitud del vehículo: " + latitud + ", Longitud del vehículo: " + longitud);

        return configuracion.getZonasRestringidas().stream().anyMatch(zona -> {
            // Obtener los límites de la zona
            double latNoroeste = zona.getNoroeste().getLat();
            double lonNoroeste = zona.getNoroeste().getLon();
            double latSureste = zona.getSureste().getLat();
            double lonSureste = zona.getSureste().getLon();


            // Verificación de si el vehículo está dentro de los límites de la zona
            boolean dentroDeLaZona = latitud >= latSureste && latitud <= latNoroeste &&
                    longitud >= lonNoroeste && longitud <= lonSureste;

            return dentroDeLaZona;
        });
    }

    private double calcularDistancia(Double lat1, Double lon1, Double lat2, Double lon2) {
        // Mostrar las coordenadas originales para depuración
        System.out.println("Calculando distancia entre las siguientes coordenadas:");
        System.out.println("Coordenadas de la agencia: Lat: " + lat1 + ", Lon: " + lon1);
        System.out.println("Coordenadas del vehículo: Lat: " + lat2 + ", Lon: " + lon2);

        // Convertir las coordenadas geográficas en un sistema de coordenadas plano (Euler XY)
        // Utilizamos un factor de escala de 111 km por grado de latitud
        double lat1EnKm = lat1 * 111; // Conversion a distancia en kilómetros (latitud)
        double lon1EnKm = lon1 * 111 * Math.cos(Math.toRadians(lat1)); // Convertir longitud a kilómetros usando latitud
        double lat2EnKm = lat2 * 111; // Conversion a distancia en kilómetros (latitud)
        double lon2EnKm = lon2 * 111 * Math.cos(Math.toRadians(lat2)); // Convertir longitud a kilómetros usando latitud



        // Aplicar la fórmula de distancia en el plano XY (Pitagoras)
        double deltaX = lon2EnKm - lon1EnKm;
        double deltaY = lat2EnKm - lat1EnKm;
        double distancia = Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));



        return distancia;
    }


}
