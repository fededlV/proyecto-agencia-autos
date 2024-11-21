package org.fede.servicioreportes.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Configuracion {

    private Coordenada coordenadasAgencia;
    private double radioAdmitidoKm;
    private List<ZonaRestringida> zonasRestringidas;

    public Configuracion(Coordenada coordenadasAgencia, double radioAdmitidoKm, List<ZonaRestringida> zonasRestringidas) {
        this.coordenadasAgencia = coordenadasAgencia;
        this.radioAdmitidoKm = radioAdmitidoKm;
        this.zonasRestringidas = zonasRestringidas;
    }
}
