package org.fede.pruebas.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
    @JsonBackReference
    @JoinColumn(name = "id_marca")
    private Marca marca;
    private String descripcion;

    public Modelo() {
    }

    public Modelo(String descripcion) {
        this.descripcion = descripcion;
    }
}
