package org.fede.pruebas.services;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CoordenadaService {

    private final ConfigService configService;

    public CoordenadaService(ConfigService configService) {
        this.configService = configService;
    }

    public static class Configuracion {
        public double latitud;
        public double longitud;
        public double radioMaximo;
        public List<ZonaPeligrosa> zonasPeligrosas;
        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("Configuración [")
                    .append("Latitud: ").append(latitud)
                    .append(", Longitud: ").append(longitud)
                    .append(", Radio Máximo: ").append(radioMaximo)
                    .append(", Zonas Peligrosas: ").append(zonasPeligrosas)
                    .append("]");
            return sb.toString();
        }
    }

    public static class ZonaPeligrosa {
        public double latitud;
        public double longitud;

        @Override
        public String toString() {
            return "ZonaPeligrosa{" +
                    "latitud=" + latitud +
                    ", longitud=" + longitud +
                    '}';
        }
    }

    public static class PosicionVehiculo {
        public double latitud;
        public double longitud;
    }

    private double calcularDistancia(double lat1, double lon1, double lat2, double lon2) {
        return Math.sqrt(Math.pow(lat2 - lat1, 2) + Math.pow(lon2 - lon1, 2));
    }

    public boolean estaDentroDelRadio(PosicionVehiculo posicionVehiculo) {
        Configuracion config = configService.obtenerConfiguracion();
        double distancia = calcularDistancia(config.latitud, config.longitud, posicionVehiculo.latitud, posicionVehiculo.longitud);
        return distancia <= config.radioMaximo;
    }

    public boolean estaEnZonaPeligrosa(PosicionVehiculo posicionVehiculo) {
        Configuracion config = configService.obtenerConfiguracion();
        for (ZonaPeligrosa zona : config.zonasPeligrosas) {
            double distancia = calcularDistancia(zona.latitud, zona.longitud, posicionVehiculo.latitud, posicionVehiculo.longitud);
            if (distancia == 0) {
                return true;
            }
        }
        return false;
    }

    public void evaluarPosicionVehiculo(PosicionVehiculo posicionVehiculo, boolean enPrueba) {
        if (!enPrueba) return;

        if (!estaDentroDelRadio(posicionVehiculo) || estaEnZonaPeligrosa(posicionVehiculo)) {
            notificarEncargado();
            restringirCliente();
        }
    }

    private void notificarEncargado() {
        // Implementación para enviar notificación al encargado
    }

    private void restringirCliente() {
        // Implementación para agregar al cliente a la lista de clientes restringidos
    }

}
