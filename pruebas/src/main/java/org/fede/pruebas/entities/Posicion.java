package org.fede.pruebas.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
public class Posicion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    @Column(name = "fecha_hora", nullable = false)
    private LocalDateTime fechaHora;

    @Column(nullable = false)
    private Double latitud;

    @Column(nullable = false)
    private Double longitud;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "id_vehiculo")
    private Vehiculo vehiculo;

    public Posicion() {
    }

    public Posicion(LocalDateTime fechaHora, Double latitud, Double longitud, Vehiculo vehiculo) {
        this.fechaHora = fechaHora;
        this.latitud = latitud;
        this.longitud = longitud;
        this.vehiculo = vehiculo;
    }
}
