package org.fede.pruebas.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
public class Notificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "interesado_id", nullable = false)
    private Interesado interesado;

    @Column(nullable = false)
    private String mensaje;

    @Column(name = "fecha_envio", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime fechaEnvio;

    public Notificacion() {
    }

    public Notificacion(Interesado interesado, String mensaje, LocalDateTime fechaEnvio) {
        this.interesado = interesado;
        this.mensaje = mensaje;
        this.fechaEnvio = fechaEnvio;
    }
}
