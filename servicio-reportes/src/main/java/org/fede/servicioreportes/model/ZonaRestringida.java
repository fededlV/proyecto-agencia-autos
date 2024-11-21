package org.fede.servicioreportes.model;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ZonaRestringida {
    private Coordenada noroeste;
    private Coordenada sureste;

    public ZonaRestringida(Coordenada sureste, Coordenada noroeste) {
        this.sureste = sureste;
        this.noroeste = noroeste;
    }
}
