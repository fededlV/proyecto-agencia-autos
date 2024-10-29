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
public class Prueba {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, name = "fecha_hora_inicio")
    private LocalDateTime fechaHoraInicio;

    @Column(nullable = false, name = "fecha_hora_fin")
    private LocalDateTime fechaHoraFin;

    @Column(length = 500)
    private String comentarios;

    @ManyToOne
    @JoinColumn(name = "id_vehiculo")
    private Vehiculo vehiculo;

    @ManyToOne
    @JoinColumn(name = "id_interesado")
    private Interesado interesado;

    @ManyToOne
    @JoinColumn(name = "id_empleado")
    private Empleado empleado;

    public Prueba() {
    }

    public Prueba(LocalDateTime fechHoraInicio, String comentarios, LocalDateTime fechHoraFin, Vehiculo vehiculo, Interesado interesado, Empleado empleado) {
        this.fechaHoraInicio = fechHoraInicio;
        this.comentarios = comentarios;
        this.fechaHoraFin = fechHoraFin;
        this.vehiculo = vehiculo;
        this.interesado = interesado;
        this.empleado = empleado;
    }
}