package org.fede.pruebas.services;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Service
public class ConfigService {

    private static final String CONFIG_URL = "https://labsys.frc.utn.edu.ar/apps-disponibilizadas/backend/api/v1/configuracion/";

    public CoordenadaService.Configuracion obtenerConfiguracion() {
        try {
            URL url = new URL(CONFIG_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String output;
            while ((output = br.readLine()) != null) {
                sb.append(output);
            }
            br.close();
            conn.disconnect();

            // Parseo del JSON
            JSONObject json = new JSONObject(sb.toString());
            CoordenadaService.Configuracion config = new CoordenadaService.Configuracion();

            // Coordenadas de la agencia
            JSONObject coordAgencia = json.getJSONObject("coordenadasAgencia");
            config.latitud = coordAgencia.getDouble("lat");
            config.longitud = coordAgencia.getDouble("lon");

            // Radio admitido
            config.radioMaximo = json.getDouble("radioAdmitidoKm");

            // Zonas restringidas
            config.zonasPeligrosas = new ArrayList<>();
            JSONArray zonasRestringidas = json.getJSONArray("zonasRestringidas");
            for (int i = 0; i < zonasRestringidas.length(); i++) {
                JSONObject zonaObj = zonasRestringidas.getJSONObject(i);
                JSONObject noroeste = zonaObj.getJSONObject("noroeste");
                JSONObject sureste = zonaObj.getJSONObject("sureste");

                CoordenadaService.ZonaPeligrosa nuevaZona = new CoordenadaService.ZonaPeligrosa();
                nuevaZona.latitud = (noroeste.getDouble("lat") + sureste.getDouble("lat")) / 2;
                nuevaZona.longitud = (noroeste.getDouble("lon") + sureste.getDouble("lon")) / 2;

                config.zonasPeligrosas.add(nuevaZona);


            }

            return config;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public static void main(String[] args) {
        ConfigService configService = new ConfigService();
        CoordenadaService.Configuracion config = configService.obtenerConfiguracion();
        System.out.println("Configuracion: " + config);
    }
}
