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
public class Marca {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nombre;
    @OneToMany(mappedBy = "marca")
    private List<Modelo> modelos;

    public Marca() {
    }

    public Marca(String nombre) {
        this.nombre = nombre;
    }
}
