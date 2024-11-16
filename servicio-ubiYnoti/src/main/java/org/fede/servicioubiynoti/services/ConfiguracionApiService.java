package org.fede.servicioubiynoti.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;

@Service
public class ConfiguracionApiService {

    private static final String API_URL = "https://labsys.frc.utn.edu.ar/apps-disponibilizadas/backend/api/v1/configuracion/";

    public ConfiguracionApi obtenerConfiguracion() {
        RestTemplate restTemplate = new RestTemplate();
        String json = restTemplate.getForObject(API_URL, String.class);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode root = objectMapper.readTree(json);

            // leer localizacion agencia y el radio admitido
            double agenciaLat = root.path("coordenadasAgencia").path("lat").asDouble();
            double agenciaLon = root.path("coordenadasAgencia").path("lon").asDouble();
            double radioAdmitidoKm = root.path("radioAdmitidoKm").asDouble();

            // leer zonas restringidas en la API
            List<ZonaRestringida> zonasRestringidas = objectMapper.readerForListOf(ZonaRestringida.class)
                    .readValue(root.path("zonasRestringidas"));

            return new ConfiguracionApi(agenciaLat, agenciaLon, radioAdmitidoKm, zonasRestringidas);

        } catch (IOException e) {
            throw new RuntimeException("Error al procesar la respuesta de la API", e);
        }
    }
}
