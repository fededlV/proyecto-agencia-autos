package org.fede.pruebas.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
public class Modelo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "id_marca")
    private Marca marca;
    private String descripcion;

    public Modelo() {
    }

    public Modelo(String descripcion) {
        this.descripcion = descripcion;
    }
}
