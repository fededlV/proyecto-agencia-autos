package org.fede.pruebas.dto;
import java.util.List;

public class ConfiguracionExterna {
    private Coordenadas coordenadasAgencia;
    private double radioAdmitidoKm;
    private List<ZonaRestringida> zonasRestringidas;

    // Getters y Setters


    public org.fede.pruebas.dto.ConfiguracionExterna.Coordenadas getCoordenadasAgencia() {
        return coordenadasAgencia;
    }

    public void setCoordenadasAgencia(org.fede.pruebas.dto.ConfiguracionExterna.Coordenadas coordenadasAgencia) {
        this.coordenadasAgencia = coordenadasAgencia;
    }

    public double getRadioAdmitidoKm() {
        return radioAdmitidoKm;
    }

    public void setRadioAdmitidoKm(double radioAdmitidoKm) {
        this.radioAdmitidoKm = radioAdmitidoKm;
    }

    public List<ZonaRestringida> getZonasRestringidas() {
        return zonasRestringidas;
    }

    public void setZonasRestringidas(List<ZonaRestringida> zonasRestringidas) {
        this.zonasRestringidas = zonasRestringidas;
    }

    public static class Coordenadas {
        private double lat;
        private double lon;

        // Getters y Setters

        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }

        public double getLon() {
            return lon;
        }

        public void setLon(double lon) {
            this.lon = lon;
        }
    }

    public static class ZonaRestringida {
        private Coordenadas noroeste;
        private Coordenadas sureste;

        // Getters y Setters

        public org.fede.pruebas.dto.ConfiguracionExterna.Coordenadas getNoroeste() {
            return noroeste;
        }

        public void setNoroeste(org.fede.pruebas.dto.ConfiguracionExterna.Coordenadas noroeste) {
            this.noroeste = noroeste;
        }

        public org.fede.pruebas.dto.ConfiguracionExterna.Coordenadas getSureste() {
            return sureste;
        }

        public void setSureste(org.fede.pruebas.dto.ConfiguracionExterna.Coordenadas sureste) {
            this.sureste = sureste;
        }
    }
}
