package org.fede.servicioreportes.entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Coordenada {

    private double latitud;
    private double longitud;

    public Coordenada(double latitud, double longitud) {
        this.latitud = latitud;
        this.longitud = longitud;
    }


}
