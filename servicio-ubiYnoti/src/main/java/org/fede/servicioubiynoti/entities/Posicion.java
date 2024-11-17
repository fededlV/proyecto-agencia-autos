package org.fede.servicioubiynoti.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
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
    public Posicion(double latitud, double longitud, LocalDateTime fechaHora) {
        this.latitud = latitud;
        this.longitud = longitud;
        this.fechaHora = fechaHora;
    }

    // Getters y setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }
}
