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
public class Marcas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    @OneToMany(mappedBy = "marca")
    private List<Modelos> modelos;

    public Marcas() {
    }

    public Marcas(String nombre) {
        this.nombre = nombre;
    }
}
