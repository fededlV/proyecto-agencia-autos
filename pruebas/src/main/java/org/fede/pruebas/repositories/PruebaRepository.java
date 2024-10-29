package org.fede.pruebas.repositories;

import org.fede.pruebas.entities.Prueba;
import org.fede.pruebas.entities.Vehiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

public interface PruebaRepository extends JpaRepository<Prueba, Integer> {

    boolean existsByVehiculoAndFechaHoraInicioLessThanEqualAndFechaHoraFinGreaterThanEqual(
            Vehiculo vehiculo, LocalDateTime fecha_hora_inicio, LocalDateTime fecha_hora_fin
    );
}
