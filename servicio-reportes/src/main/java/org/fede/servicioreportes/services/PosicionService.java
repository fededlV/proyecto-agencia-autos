package org.fede.servicioreportes.services;

import org.fede.servicioreportes.dto.Coordenadas;
import org.fede.servicioreportes.dto.ConfiguracionDto;
import org.fede.servicioreportes.entities.Coordenada;
import org.fede.servicioreportes.entities.Posicion;
import org.fede.servicioreportes.entities.ZonaRestringida;
import org.fede.servicioreportes.repositories.PosicionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PosicionService {

    private final ConfiguracionService configuracionService;
    //Agrugue FEDE
    @Autowired
    private PosicionRepository posicionRepository;

    public List<Posicion> obtenerPosicionesPorVehiculoYPeriodo(Integer vehiculoId, LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        return posicionRepository.findPosicionByVehiculoAndPeriodo(vehiculoId, fechaInicio, fechaFin);
    }

    public boolean estaFueraDeRango(double latitudAgencia, double longitudAgencia, double latitudVehiculo, double longitudVehiculo, double radioAdmitido) {
        double distancia = calcularDistanciaEuclidea(latitudAgencia, longitudAgencia, latitudVehiculo, longitudVehiculo);
        return distancia > radioAdmitido;
    }

    public boolean estaEnZonaPeligrosa(List<ZonaRestringida> zonaRestringidas, double latitudVehiculo, double longitudVehiculo) {
        for (ZonaRestringida zona : zonaRestringidas) {
            Coordenada noroeste = zona.getNoroeste();
            Coordenada sureste = zona.getSureste();
            if (latitudVehiculo >= noroeste.getLatitud() && latitudVehiculo <= sureste.getLatitud() &&
                    longitudVehiculo >= noroeste.getLongitud() && longitudVehiculo <= sureste.getLongitud()) {
                return true;
            }
        }
        return false;
    }

    private double calcularDistanciaEuclidea(double latitud1, double longitud1, double latitud2, double longitud2) {
        return Math.sqrt(Math.pow(latitud2 - latitud1, 2) + Math.pow(longitud2 - longitud1, 2));
    }
    //Hasta aca agregue FEDE.

    // Constructor que inyecta ConfiguracionService
    public PosicionService(ConfiguracionService configuracionService) {
        this.configuracionService = configuracionService;
    }

    public boolean validarUbicacion(Long vehiculoId) {
        // Obtenemos la ubicación del vehículo
        Coordenada ubicacionVehiculo = obtenerUbicacionVehiculo(vehiculoId);

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

        //Método para obtener la ubicación del vehículo (a través de alguna API o base de datos)
        Coordenada obtenerUbicacionVehiculo(Long vehiculoId) {
        // Lógica para obtener las coordenadas del vehículo, posiblemente utilizando RestTemplate o WebClient
        return new Coordenada(0.0, 0.0);  // Aquí iría la lógica real de obtención de coordenadas
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
    private double calcularDistancia(Coordenada punto1, Coordenada punto2) {
        // Lógica para calcular la distancia entre las dos coordenadas
        return 0.0;  // Aquí iría la implementación real de cálculo de distancia
    }

    // Método para determinar si una coordenada está dentro de una zona restringida
    private boolean esZonaRestringida(Coordenada ubicacionVehiculo, ConfiguracionDto.ZonaRestringida zona) {
        // Lógica para verificar si las coordenadas del vehículo están dentro de los límites de la zona restringida
        return false;  // Aquí iría la lógica real para comprobar si está dentro de la zona restringida
    }
}