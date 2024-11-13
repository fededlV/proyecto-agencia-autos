package org.fede.servicioreportes.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Coordenadas {

    private double lat;
    private double lon;

    public Coordenadas() {}

    public Coordenadas(double lat, double lon) {
        this.lat = lat;
        this.lon = lon;
    }
}