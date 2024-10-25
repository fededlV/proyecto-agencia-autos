package org.fede.pruebas.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
public class Posiciones {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(name = "fecha_hora", nullable = false)
    private LocalDateTime fechaHora;

    @Column(nullable = false)
    private Double latitud;

    @Column(nullable = false)
    private Double longitud;

    @ManyToOne
    @Column(nullable = false)
    private Vehiculos vehiculo;

    public Posiciones() {
    }

    public Posiciones(LocalDateTime fechaHora, Double latitud, Double longitud, Vehiculos vehiculo) {
        this.fechaHora = fechaHora;
        this.latitud = latitud;
        this.longitud = longitud;
        this.vehiculo = vehiculo;
    }
}
