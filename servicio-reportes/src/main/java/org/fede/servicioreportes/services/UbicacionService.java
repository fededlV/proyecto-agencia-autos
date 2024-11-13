package org.fede.servicioreportes.services;

import org.fede.servicioreportes.dto.Coordenadas;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class UbicacionService {

    private WebClient webClient;

    @Value("${external.api.configuracion-url}")
    private String configuracionUrl;

    public UbicacionService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }


    public Coordenadas obtenerUbicacionVehiculo(Long vehiculoId){
        String url = configuracionUrl + "/ubicacion/vehiculos/" + vehiculoId + "/posicion";

        //Realizamos la solicitud GET y obtenemos las coordenadas
        Mono<Coordenadas> response = webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(Coordenadas.class);
        return response.block();
    }
}
