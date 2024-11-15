package org.fede.servicioubiynoti.entities;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Configuracion {

    // Usamos la clase externa `Coordenadas` del paquete `dto`
    private Coordenada coordenadasAgencia;
    private double radioAdmitidoKm;
    private List<ZonaRestringida> zonasRestringidas;
}
