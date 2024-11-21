package org.fede.servicioreportes.services;

import org.fede.servicioreportes.model.Configuracion;
import org.fede.servicioreportes.model.Coordenada;
import org.fede.servicioreportes.model.ZonaRestringida;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
public class ConfiguracionService {

    private final WebClient webClient;

    public ConfiguracionService(@Value("${external.api.configuracion-url}") String gatewatUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(gatewatUrl)
                .build();
    }

    //Metodo para obtener toda la configuracion desde la URL
    public Configuracion obtenerConfiguracion() {
        return webClient.get()
                .retrieve()
                .bodyToMono(Configuracion.class)
                .block();
    }

    //Metodos especificos para obtener cada parametro

    public Coordenada obtenerUbicacionAgencia() {
        return obtenerConfiguracion().getCoordenadasAgencia();
    }

    public double obtenerRadioPermitido() {
        return obtenerConfiguracion().getRadioAdmitidoKm();
    }

    public List<ZonaRestringida> obtenerZonasRestringida() {
        return obtenerConfiguracion().getZonasRestringidas();
    }
}
