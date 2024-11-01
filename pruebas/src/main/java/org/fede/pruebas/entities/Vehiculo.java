package org.fede.pruebas.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Entity
@Getter
@Setter
@ToString
public class Vehiculo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 50, nullable = false)
    private String patente;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "id_modelo")
    private Modelo modelo;

    @OneToMany(mappedBy = "vehiculo")
    private List<Posicion> posiciones;

    @OneToMany(mappedBy = "vehiculo")
    private List<Prueba> pruebas;

    public Vehiculo() {
    }

    public Vehiculo(String patente, Integer anio) {
        this.patente = patente;
    }
}
