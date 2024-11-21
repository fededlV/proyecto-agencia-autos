package org.fede.servicioreportes.entities;

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
@Table(name = "Posiciones")
public class Posicion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    @Column(name = "fecha_hora")
    private LocalDateTime fechaHora;

    @Column(nullable = false)
    private double latitud;

    @Column(nullable = false)
    private double longitud;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "id_vehiculo")
    private Vehiculo vehiculo;

    public Posicion() {
    }

    public Posicion(LocalDateTime fechaHora, double latitud, double longitud, Vehiculo vehiculo) {
        this.fechaHora = fechaHora;
        this.latitud = latitud;
        this.longitud = longitud;
        this.vehiculo = vehiculo;
    }
}
