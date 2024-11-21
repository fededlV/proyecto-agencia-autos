package org.fede.servicioreportes.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Coordenada {

    double latitud;
    double longitud;

    public Coordenada(double latitud, double longitud) {
        this.latitud = latitud;
        this.longitud = longitud;
    }


}
