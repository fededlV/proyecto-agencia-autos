package org.fede.servicioubiynoti.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@Table(name = "Notificaciones")
public class Notificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "empleado_id", nullable = false)
    private Integer legajo;

    @Column(name = "mensaje",nullable = false)
    private String mensaje;

    @Column(name = "fecha_envio", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime fechaEnvio;

    @Column(name = "es_incidente", nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean esIncidente = false;

    // Constructor vacío
    public Notificacion() {
    }

    // Constructor con parámetros
    public Notificacion(Integer legajo, String mensaje, LocalDateTime fechaEnvio, Boolean esIncidente) {
        this.legajo = legajo;
        this.mensaje = mensaje;
        this.fechaEnvio = fechaEnvio;
        this.esIncidente = esIncidente;
    }
}
