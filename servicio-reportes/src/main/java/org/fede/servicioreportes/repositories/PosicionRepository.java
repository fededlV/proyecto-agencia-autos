package org.fede.servicioreportes.repositories;

import org.fede.servicioreportes.entities.Posicion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface PosicionRepository extends JpaRepository<Posicion, Integer> {
    List<Posicion> findByVehiculoIdAndTimestampBetween(Long vehiculoId, LocalDateTime start, LocalDateTime end);
}
