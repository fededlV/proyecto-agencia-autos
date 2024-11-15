package org.fede.servicioubiynoti.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class ConfiguracionDto {

    // Usamos la clase externa `Coordenadas` del paquete `dto`
    private Coordenadas coordenadasAgencia;
    private double radioAdmitidoKm;
    private List<ZonaRestringida> zonasRestringidas;

    @Getter
    @Setter
    public static class ZonaRestringida {
        private Coordenadas noroeste;
        private Coordenadas sureste;
    }
}
