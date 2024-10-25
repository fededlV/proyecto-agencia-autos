package org.fede.pruebas.entities;

import jakarta.persistence.*;
import jdk.dynalink.linker.LinkerServices;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Entity
@Getter
@Setter
@ToString
public class Vehiculos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false)
    private String patetente;

    @Column(nullable = false)
    private Integer anio;

    @ManyToOne
    @JoinColumn(name = "id_modelo")
    private Modelos modelo;

    @OneToMany(mappedBy = "vehiculo")
    private List<Posiciones> posiciones;

    @OneToMany(mappedBy = "vehiculo")
    private List<Pruebas> pruebas;

    public Vehiculos() {
    }

    public Vehiculos(String patetente, Integer anio) {
        this.patetente = patetente;
        this.anio = anio;
    }
}
