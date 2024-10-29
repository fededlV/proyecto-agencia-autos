package org.fede.pruebas.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Entity
@Getter
@Setter
@ToString
@Table(name = "Empleados")
public class Empleado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer legajo;

    @Column(nullable = false, length = 30)
    private String nombre;

    @Column(length = 50, nullable = false)
    private String apellido;

    @Column(name = "telefono_contacto", nullable = false)
    private Integer telefono;

    @OneToMany(mappedBy = "empleado")
    private List<Prueba> pruebas;

    public Empleado() {
    }

    public Empleado(String nombre, String apellido, Integer telefono, List<Prueba> pruebas) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.pruebas = pruebas;
    }
}
