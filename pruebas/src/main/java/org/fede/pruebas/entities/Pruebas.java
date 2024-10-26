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
public class Pruebas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private LocalDateTime fecha_hora_inicio;

    @Column(nullable = false)
    private LocalDateTime fecha_hora_fin;

    @Column(length = 500)
    private String comentarios;

    @ManyToOne
    @JoinColumn(name = "id_vehiculo")
    private Vehiculos vehiculo;

    @ManyToOne
    @JoinColumn(name = "id_interesado")
    private Interesados interesado;

    @ManyToOne
    @JoinColumn(name = "id_empleado")
    private Empleados empleado;

    public Pruebas() {
    }

    public Pruebas(LocalDateTime fecha_hora_inicio, String comentarios, LocalDateTime fecha_hora_fin, Vehiculos vehiculo, Interesados interesado, Empleados empleado) {
        this.fecha_hora_inicio = fecha_hora_inicio;
        this.comentarios = comentarios;
        this.fecha_hora_fin = fecha_hora_fin;
        this.vehiculo = vehiculo;
        this.interesado = interesado;
        this.empleado = empleado;
    }
}
