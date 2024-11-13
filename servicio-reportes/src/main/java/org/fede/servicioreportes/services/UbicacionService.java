package org.fede.servicioreportes.services;

import org.fede.servicioreportes.dto.Coordenadas;
import org.fede.servicioreportes.dto.ConfiguracionDto;
import org.springframework.stereotype.Service;

@Service
public class UbicacionService {

    private final ConfiguracionService configuracionService;

    // Constructor que inyecta ConfiguracionService
    public UbicacionService(ConfiguracionService configuracionService) {
        this.configuracionService = configuracionService;
    }

    public boolean validarUbicacion(Long vehiculoId) {
        // Obtenemos la ubicación del vehículo
        Coordenadas ubicacionVehiculo = obtenerUbicacionVehiculo(vehiculoId);

        // Obtenemos la configuración externa (coordenadas de la agencia, zonas restringidas, etc.)
        ConfiguracionDto configuracion = configuracionService.obtenerConfiguracion();

        // Verificar si el vehículo está dentro del radio admitido
        boolean estaEnRadioAdmitido = estaDentroDelRadioAdmitido(ubicacionVehiculo, configuracion);
        if (!estaEnRadioAdmitido) {
            return false;  // El vehículo está fuera del radio admitido
        }

        // Verificar si el vehículo está dentro de una zona restringida
        boolean estaEnZonaRestringida = estaEnZonaRestringida(ubicacionVehiculo, configuracion);
        if (estaEnZonaRestringida) {
            return false;  // El vehículo está en una zona restringida
        }

        // Si pasa ambas verificaciones, el vehículo está dentro de los límites establecidos
        return true;
    }

//     Método para obtener la ubicación del vehículo (a través de alguna API o base de datos)
Coordenadas obtenerUbicacionVehiculo(Long vehiculoId) {
        // Lógica para obtener las coordenadas del vehículo, posiblemente utilizando RestTemplate o WebClient
        return new Coordenadas(0.0, 0.0);  // Aquí iría la lógica real de obtención de coordenadas
    }

    // Método para verificar si está dentro del radio admitido
    private boolean estaDentroDelRadioAdmitido(Coordenadas ubicacionVehiculo, ConfiguracionDto configuracion) {
        double distancia = calcularDistancia(ubicacionVehiculo, configuracion.getCoordenadasAgencia());
        return distancia <= configuracion.getRadioAdmitidoKm();
    }

    // Método para verificar si está dentro de una zona restringida
    private boolean estaEnZonaRestringida(Coordenadas ubicacionVehiculo, ConfiguracionDto configuracion) {
        for (ConfiguracionDto.ZonaRestringida zona : configuracion.getZonasRestringidas()) {
            if (esZonaRestringida(ubicacionVehiculo, zona)) {
                return true;
            }
        }
        return false;
    }

    // Método para calcular la distancia entre dos coordenadas
    private double calcularDistancia(Coordenadas punto1, Coordenadas punto2) {
        // Lógica para calcular la distancia entre las dos coordenadas
        return 0.0;  // Aquí iría la implementación real de cálculo de distancia
    }

    // Método para determinar si una coordenada está dentro de una zona restringida
    private boolean esZonaRestringida(Coordenadas ubicacionVehiculo, ConfiguracionDto.ZonaRestringida zona) {
        // Lógica para verificar si las coordenadas del vehículo están dentro de los límites de la zona restringida
        return false;  // Aquí iría la lógica real para comprobar si está dentro de la zona restringida
    }
}