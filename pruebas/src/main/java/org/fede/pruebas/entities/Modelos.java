package org.fede.pruebas.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
public class Modelos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "id_marca")
    private Marcas marca;
    private String descripcion;

    public Modelos() {
    }

    public Modelos(String descripcion) {
        this.descripcion = descripcion;
    }
}
