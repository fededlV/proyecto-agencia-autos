package org.fede.servicioreportes.repositories;

import org.fede.servicioreportes.entities.Posicion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface PosicionRepository extends JpaRepository<Posicion, Integer> {
    @Query("SELECT p FROM Posicion p WHERE p.vehiculo.id = :vehiculoId AND p.fechaHora BETWEEN :fechaInicio AND :fechaFin ORDER BY p.fechaHora ASC ")
    List<Posicion> findPosicionByVehiculoAndPeriodo(@Param("vehiculoId") Integer vehiculoId,
                                                    @Param("fechaInicio") LocalDateTime fechaInicio,
                                                    @Param("fechaFin") LocalDateTime fechaFin);

    List<Posicion> findByVehiculoId(Integer vehiculoId);
}
