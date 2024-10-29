package org.fede.pruebas.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
public class Interesado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    @Column(name = "tipo_documento", length = 3, nullable = false)
    private String tipoDoc;

    @Column(nullable = false, length = 10)
    private String documento;

    @Column(nullable = false, length = 50)
    private String nombre;

    @Column(nullable = false, length = 50)
    private String apellido;

    @Column(nullable = false)
    private Boolean restringido;

    @Column(name = "nro_licencia", nullable = false, unique = true)
    private Integer nroLicencia;


    @Column(name = "fecha_vencimiento_licencia", nullable = false)
    private LocalDateTime fechaVenLicencia;

    @OneToMany(mappedBy = "interesado")
    private List<Prueba> pruebas;

    public Interesado() {
    }

    public Interesado(String tipoDoc, String documento, String nombre, String apellido, Boolean restringido, Integer nroLicencia, LocalDateTime fechaVenLicencia, List<Prueba> pruebas) {
        this.tipoDoc = tipoDoc;
        this.documento = documento;
        this.nombre = nombre;
        this.apellido = apellido;
        this.restringido = restringido;
        this.nroLicencia = nroLicencia;
        this.fechaVenLicencia = fechaVenLicencia;
        this.pruebas = pruebas;
    }
}
