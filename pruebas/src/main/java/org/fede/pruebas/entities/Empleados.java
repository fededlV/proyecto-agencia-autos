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
public class Empleados {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long legajo;

    @Column(nullable = false, length = 30)
    private String nombre;

    @Column(length = 50, nullable = false)
    private String apellido;

    @Column(name = "telefono_contacto", nullable = false)
    private Integer telefono;

    @OneToMany(mappedBy = "empleado")
    private List<Pruebas> pruebas;

    public Empleados() {
    }

    public Empleados(String nombre, String apellido, Integer telefono, List<Pruebas> pruebas) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.pruebas = pruebas;
    }
}
