package org.fede.servicioubiynoti.entities;

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
@Table(name = "Pruebas")
public class Prueba {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, name = "FECHA_HORA_INICIO")
    private LocalDateTime fechaHoraInicio;

    @Column(nullable = true, name = "FECHA_HORA_FIN")
    private LocalDateTime fechaHoraFin;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "ID_VEHICULO") // Actualizado para coincidir con la base
    private Vehiculo vehiculo;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "ID_INTERESADO")
    private Interesado interesado;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "ID_EMPLEADO")
    private Empleado empleado;

    @Column(length = 500)
    private String comentarios;

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

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public Interesado getInteresado() {
        return interesado;
    }

    public void setInteresado(Interesado interesado) {
        this.interesado = interesado;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public LocalDateTime getFechaHoraFin() {
        return fechaHoraFin;
    }

    public void setFechaHoraFin(LocalDateTime fechaHoraFin) {
        this.fechaHoraFin = fechaHoraFin;
    }

    public LocalDateTime getFechaHoraInicio() {
        return fechaHoraInicio;
    }

    public void setFechaHoraInicio(LocalDateTime fechaHoraInicio) {
        this.fechaHoraInicio = fechaHoraInicio;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
