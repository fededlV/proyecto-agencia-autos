package org.fede.servicioubiynoti.dto;

import java.util.List;

public class ConfiguracionDTO {
    private Coordenadas coordenadasAgencia;
    private double radioAdmitidoKm;
    private List<ZonaRestringida> zonasRestringidas;

    public Coordenadas getCoordenadasAgencia() {
        return coordenadasAgencia;
    }

    public void setCoordenadasAgencia(Coordenadas coordenadasAgencia) {
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

        // Constructor vacío
        public Coordenadas() {
        }

        // Constructor con parámetros
        public Coordenadas(double lat, double lon) {
            this.lat = lat;
            this.lon = lon;
        }

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

        public ZonaRestringida(org.fede.servicioubiynoti.dto.ConfiguracionDTO.Coordenadas noroeste, org.fede.servicioubiynoti.dto.ConfiguracionDTO.Coordenadas sureste) {
            this.noroeste = noroeste;
            this.sureste = sureste;
        }

        public ZonaRestringida() {
        }

        public Coordenadas getNoroeste() {
            return noroeste;
        }

        public void setNoroeste(Coordenadas noroeste) {
            this.noroeste = noroeste;
        }

        public Coordenadas getSureste() {
            return sureste;
        }

        public void setSureste(Coordenadas sureste) {
            this.sureste = sureste;
        }
    }
}
