package org.fede.servicioreportes.services;

import org.fede.servicioreportes.dto.Coordenadas;
import org.fede.servicioreportes.dto.ConfiguracionDto;
import org.fede.servicioreportes.dto.IncidenteDTO;
import org.fede.servicioreportes.entities.Prueba;
import org.fede.servicioreportes.repositories.PruebaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReporteService {

    @Autowired
    private PruebaRepository pruebaRepository;

    @Autowired
    private ConfiguracionService configuracionService;

    @Autowired
    private UbicacionService ubicacionService;

    // Punto i. Reporte de incidentes
    public List<IncidenteDTO> obtenerIncidentes() {
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
    }

    private boolean excedeRadioAdmitido(Coordenadas agencia, Coordenadas actual, double radioAdmitidoKm){
        double distancia = calcularDistancia(agencia, actual);
        return distancia > radioAdmitidoKm;
    }

    private boolean estaEnZonaRestringida(Coordenadas ubicacion, List<ConfiguracionDto.ZonaRestringida> zonasRestringidas) {
        for (ConfiguracionDto.ZonaRestringida zona : zonasRestringidas) {
            Coordenadas noroeste = zona.getNoroeste();
            Coordenadas sureste = zona.getSureste();
            if (ubicacion.getLat() >= noroeste.getLat() && ubicacion.getLat() <= sureste.getLat() &&
                    ubicacion.getLon() >= noroeste.getLon() && ubicacion.getLon() <= sureste.getLon()) {
                return true;
            }
        }
        return false;
    }

    private double calcularDistancia(Coordenadas c1, Coordenadas c2) {
        final int RADIO_TIERRA_KM = 6371;
        double latDiff = Math.toRadians(c2.getLat() - c1.getLat());
        double lonDiff = Math.toRadians(c2.getLon() - c1.getLon());
        double a = Math.sin(latDiff / 2) * Math.sin(latDiff / 2) +
                Math.cos(Math.toRadians(c1.getLat())) * Math.cos(Math.toRadians(c2.getLat())) *
                        Math.sin(lonDiff / 2) * Math.sin(lonDiff / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return RADIO_TIERRA_KM * c;
    }

    private IncidenteDTO crearIncidente(Prueba prueba, String descripcion) {
        IncidenteDTO incidente = new IncidenteDTO();
        incidente.setPruebaId(Long.valueOf(prueba.getId()));
        incidente.setVehiculoId(Long.valueOf(prueba.getVehiculo().getId()));
        incidente.setDescripcion(descripcion);
        incidente.setFechaIncidente(java.time.LocalDateTime.now());
        return incidente;
    }
}

