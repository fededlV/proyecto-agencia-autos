package org.fede.servicioubiynoti.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@Table(name = "Posiciones")
public class Posicion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private double latitud;
    private double longitud;
    private LocalDateTime fechaHora;

    @ManyToOne
    @JoinColumn(name = "id_vehiculo")  // Nombre de la columna que se unirá con Vehiculo
    private Vehiculo vehiculo;  // Propiedad para mapear la relación con Vehiculo

    // Constructor vacío
    public Posicion() {
    }

    // Constructor con parámetros
    public Posicion(Vehiculo vehiculo, double latitud, double longitud, LocalDateTime fechaHora) {
        this.vehiculo = vehiculo;
        this.latitud = latitud;
        this.longitud = longitud;
        this.fechaHora = fechaHora;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public double getLatitud() {
        return latitud;
    }

    public double getLongitud() {
        return longitud;
    }

}
