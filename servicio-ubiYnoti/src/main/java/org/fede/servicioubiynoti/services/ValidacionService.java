package org.fede.servicioubiynoti.services;

import org.fede.servicioubiynoti.dto.PosicionDto;
import org.springframework.stereotype.Service;

@Service
public class ValidacionService {

    private final ConfiguracionApiService configuracionApiService;

    public ValidacionService(ConfiguracionApiService configuracionApiService) {
        this.configuracionApiService = configuracionApiService;
    }

    //Valida si una posición es válida con base en el radio permitido y las zonas restringidas

    public boolean validarPosicion(PosicionDto posicionDto) {
        ConfiguracionApi config = configuracionApiService.obtenerConfiguracion();

        // Validar si la posición está dentro del radio permitido
        if (!esPosicionPermitida(posicionDto, config)) {
            return false;
        }

        // Validar si la posición está dentro de zonas restringidas
        if (esZonaRestringida(posicionDto, config)) {
            return false;
        }

        return true;
    }

    //Valida si la posición está dentro del radio permitido desde la agencia
    private boolean esPosicionPermitida(PosicionDto posicionDto, ConfiguracionApi config) {
        double distancia = calcularDistanciaPlano(
                posicionDto.getLat(),
                posicionDto.getLon(),
                config.getAgenciaLat(),
                config.getAgenciaLon()
        );

        return distancia <= config.getRadioAdmitidoKm();
    }

    //Verifica si la posición está dentro de alguna zona restringida
    private boolean esZonaRestringida(PosicionDto posicionDto, ConfiguracionApi config) {
        for (ZonaRestringida zona : config.getZonasRestringidas()) {
            if (posicionDto.getLat() >= zona.getSureste().getLat() &&
                    posicionDto.getLat() <= zona.getNoroeste().getLat() &&
                    posicionDto.getLon() >= zona.getNoroeste().getLon() &&
                    posicionDto.getLon() <= zona.getSureste().getLon()) {
                return true;
            }
        }
        return false;
    }


    private double calcularDistanciaPlano(double x1, double y1, double x2, double y2) {
        double deltaX = x2 - x1;
        double deltaY = y2 - y1;
        return Math.sqrt(deltaX * deltaX + deltaY * deltaY);
    }
}
