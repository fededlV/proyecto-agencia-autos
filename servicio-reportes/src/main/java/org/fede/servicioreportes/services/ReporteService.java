package org.fede.servicioreportes.services;

import org.fede.servicioreportes.dto.*;
import org.fede.servicioreportes.entities.*;
import org.fede.servicioreportes.repositories.PosicionRepository;
import org.fede.servicioreportes.repositories.PruebaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReporteService {


    @Autowired
    private PruebasMapper pruebasMapper;
    @Autowired
    private PruebaRepository pruebaRepository;
    @Autowired
    private ConfiguracionService configuracionService;
    @Autowired
    private PosicionService posicionService;
    @Autowired
    private PosicionRepository posicionRepository;

    // Punto i. Reporte de incidentes
    /*public List<IncidenteDTO> obtenerIncidentes() {
        // Obtener configuracion de la agencia
        ConfiguracionDto configuracion = configuracionService.obtenerConfiguracion();
        double radioAdmitido = configuracion.getRadioAdmitidoKm();
        Coordenadas agenciaCoords = configuracion.getCoordenadasAgencia();
        List<ConfiguracionDto.ZonaRestringida> zonasRestringidas = configuracion.getZonasRestringidas();

        //Obtener pruebas en curso
        List<Prueba> pruebasEnCurso = pruebaRepository.findPruebasEnCurso();
        List<IncidenteDTO> incidentes = new ArrayList<>();

        //Evaluar cada prueba
        for (Prueba prueba : pruebasEnCurso) {
            Coordenadas ubicacionActual = ubicacionService.obtenerUbicacionVehiculo(Long.valueOf(prueba.getVehiculo().getId()));

            //Verificar si esta fuera del radio admitido
            if(excedeRadioAdmitido(agenciaCoords, ubicacionActual, radioAdmitido)){
                incidentes.add(crearIncidente(prueba, "El vehiculo excedio el radio admitido de " + radioAdmitido + "km"));
                continue;
            }

            //Verificar si esta dentro de una zaon restringida
            if(estaEnZonaRestringida(ubicacionActual, zonasRestringidas)){
                incidentes.add(crearIncidente(prueba, "El vehiculo ingreso a una zona restringida"));
            }
        }
        return incidentes;
    }*/

    //Aca comienzo a meter lo mio FEDE
    //Generacion de Incidentes
    public List<IncidenteDTO> generarReporteInicidentes(Integer vehiculoId, List<ZonaRestringida> zonasRestringidas, double radioPermitido, double latitudAgencia, double longitudAgencia) {
        List<Prueba> pruebas = pruebaRepository.findPruebaByVehiculo(vehiculoId);
        List<IncidenteDTO> incidentes = new ArrayList<>();
        for (Prueba prueba : pruebas) {
            List<Posicion> posiciones = posicionService.obtenerPosicionesPorVehiculoYPeriodo(vehiculoId, prueba.getFechaHoraInicio(), prueba.getFechaHoraFin());
            for (Posicion posicion : posiciones) {
                if (posicionService.estaFueraDeRango(latitudAgencia, longitudAgencia, posicion.getLatitud(), posicion.getLongitud(), radioPermitido)) {
                    incidentes.add(
                            new IncidenteDTO(prueba.getId(),
                                    vehiculoId,
                                    prueba.getInteresado().getId(),
                                    posicion,
                                    "Fuera de radio permitido"
                            ));
                }
                if (posicionService.estaEnZonaPeligrosa(zonasRestringidas, posicion.getLatitud(), posicion.getLongitud())) {
                    incidentes.add(
                            new IncidenteDTO(prueba.getId(),
                            vehiculoId,
                            prueba.getInteresado().getId(),
                            posicion,
                            "Ingreso a zona peligrosa"
                    ));
                }
            }
        }
        return incidentes;
    }

    private boolean excedeRadioAdmitido(Coordenada agencia, Coordenada actual, double radioAdmitidoKm) {
        double distancia = calcularDistancia(agencia, actual);
        return distancia > radioAdmitidoKm;
    }

    private boolean estaEnZonaRestringida(Coordenada ubicacion, List<ZonaRestringida> zonasRestringidas) {
        for (ZonaRestringida zona : zonasRestringidas) {
            Coordenada noroeste = zona.getNoroeste();
            Coordenada sureste = zona.getSureste();
            if (ubicacion.getLatitud() >= noroeste.getLatitud() && ubicacion.getLatitud() <= sureste.getLatitud() &&
                    ubicacion.getLongitud() >= noroeste.getLongitud() && ubicacion.getLongitud() <= sureste.getLongitud()) {
                return true;
            }
        }
        return false;
    }

    //De aca para abajo esta todo bien todo correcto.
    private double calcularDistancia(Coordenada c1, Coordenada c2) {
        final int RADIO_TIERRA_KM = 6371;
        double latDiff = Math.toRadians(c2.getLatitud() - c1.getLatitud()); //Se convierte a radianes
        double lonDiff = Math.toRadians(c2.getLongitud() - c1.getLongitud()); //Se convierte a radianes
        double a = Math.sin(latDiff / 2) * Math.sin(latDiff / 2) +
                Math.cos(Math.toRadians(c1.getLatitud())) * Math.cos(Math.toRadians(c2.getLatitud())) *
                        Math.sin(lonDiff / 2) * Math.sin(lonDiff / 2); //Se implementa la formula Haversine, para calcular la distancia entre dos puntos en la tierra(esfera), calcula la distancia angular.
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a)); //Se establece el angulo entre dos puntos, donde atan2 es una funcion que calcula el arco tangente de las dos variables pasadas como parametro.
        return RADIO_TIERRA_KM * c; //Distancia en km. (Angulo central por el radio de la tierra).
    }


    //Punto 3. Cantidad de kilometros recorridos por un vehiculo.
    public double calcularKilometrosRecorridos(Integer vehiculoId, LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        //Obtiene las posiciones del vehiculo en el periodo especificado
        List<Posicion> posiciones = posicionRepository.findPosicionByVehiculoAndPeriodo(vehiculoId, fechaInicio, fechaFin);
        double distanciaTotal = 0.0;

        //calcula la distancia entre posiciones consecutivas
        for (int i = 1; i < posiciones.size(); i++) {
            Coordenada anterior = new Coordenada(posiciones.get(i - 1).getLatitud(), posiciones.get(i - 1).getLongitud());
            Coordenada actual = new Coordenada(posiciones.get(i).getLatitud(), posiciones.get(i).getLongitud());
            distanciaTotal += calcularDistancia(anterior, actual);
        }

        return distanciaTotal; // Distancia total en kilometros.
    }

    //Punto 4. Detalle de pruebas realizadas para un vehiculo
    public List<PruebaResponseDTO> obtenerPruebasPorVehiculo(Integer vehiculoId) {
        //Obtiene todas las pruebas relacionadas con el vehiculo
        List<Prueba> pruebas = pruebaRepository.findPruebaByVehiculo(vehiculoId);
        List<PruebaResponseDTO> pruebasDTOs = new ArrayList<>();

        //Covertir cada prueba en un DTO
        for (Prueba p : pruebas) {
            PruebaResponseDTO dto = pruebasMapper.toPruebaResponseDto(p);
            pruebasDTOs.add(dto);
        }
        return pruebasDTOs; //devuelve la lista de los dtos.
    }
}

