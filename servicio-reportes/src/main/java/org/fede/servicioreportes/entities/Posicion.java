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

    @Column(name = "timestamp")  // Si tu columna en la base de datos se llama "timestamp"
    private LocalDateTime timestamp;

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

    public Posicion(LocalDateTime timestamp, Double latitud, Double longitud, Vehiculo vehiculo) {
        this.timestamp = timestamp;
        this.latitud = latitud;
        this.longitud = longitud;
        this.vehiculo = vehiculo;
    }
}
