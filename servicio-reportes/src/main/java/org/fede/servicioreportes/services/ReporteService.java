package org.fede.servicioreportes.services;

import org.fede.servicioreportes.dto.PruebaDTO;
import org.fede.servicioreportes.entities.Posicion;
import org.fede.servicioreportes.entities.Prueba;
import org.fede.servicioreportes.repositories.PosicionRepository;
import org.fede.servicioreportes.repositories.PruebaRepository;
import org.fede.servicioreportes.repositories.VehiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReporteService {

    @Autowired
    private PruebaRepository pruebaRepository;

    @Autowired
    private PosicionRepository posicionRepository;

    @Autowired
    private ApiGatewayClient apiGatewayClient;

    // Reporte de incidentes (pruebas que excedieron los límites)
    public List<Prueba> obtenerIncidentes() {
        List<Prueba> incidentes = new ArrayList<>();
        List<Prueba> pruebasEnCurso = pruebaRepository.findPruebasEnCurso();

        for (Prueba prueba : pruebasEnCurso) {
            List<Posicion> posiciones = posicionRepository.findByVehiculoIdAndFechaHoraBetween(
                    prueba.getIdVehiculo(), prueba.getFechaHoraInicio(), prueba.getFechaHoraFin()
            );

            double radioMaximo = apiGatewayClient.obtenerRadioMaximo();
            List<ZonaPeligrosa> zonasPeligrosas = apiGatewayClient.obtenerZonasPeligrosas();

            for (Posicion posicion : posiciones) {
                if (calcularDistanciaDesdeAgencia(posicion) > radioMaximo || esEnZonaPeligrosa(posicion, zonasPeligrosas)) {
                    incidentes.add(prueba);
                    break;
                }
            }
        }
        return incidentes;
    }

    public List<Prueba> obtenerIncidentesPorEmpleado(Long empleadoId) {
        return pruebaRepository.findIncidentesPorEmpleado(empleadoId);
    }

    public double calcularKilometrosRecorridos(Long vehiculoId, LocalDate fechaInicio, LocalDate fechaFin) {
        List<Posicion> posiciones = posicionRepository.findByVehiculoIdAndFechaHoraBetween(
                vehiculoId, fechaInicio.atStartOfDay(), fechaFin.atTime(23, 59, 59)
        );

        double totalDistancia = 0;
        for (int i = 1; i < posiciones.size(); i++) {
            totalDistancia += calcularDistanciaEntrePosiciones(posiciones.get(i - 1), posiciones.get(i));
        }
        return totalDistancia;
    }

    public List<Prueba> obtenerPruebasPorVehiculo(Long vehiculoId) {
        return pruebaRepository.findByVehiculoId(vehiculoId);
    }

    // Métodos auxiliares para cálculos de distancia y verificación de zonas
    private double calcularDistanciaDesdeAgencia(Posicion posicion) {
        // Implementar cálculo de distancia con respecto a la latitud/longitud de la agencia
    }

    private boolean esEnZonaPeligrosa(Posicion posicion, List<ZonaPeligrosa> zonasPeligrosas) {
        // Implementar lógica para verificar si la posición está en una zona peligrosa
    }

    private double calcularDistanciaEntrePosiciones(Posicion p1, Posicion p2) {
        double latDiff = p2.getLatitud() - p1.getLatitud();
        double lonDiff = p2.getLongitud() - p1.getLongitud();
        return Math.sqrt(Math.pow(latDiff, 2) + Math.pow(lonDiff, 2));
    }
}
