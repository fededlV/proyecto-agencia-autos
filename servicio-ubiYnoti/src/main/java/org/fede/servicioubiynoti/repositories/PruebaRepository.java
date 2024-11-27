package org.fede.servicioubiynoti.repositories;

import java.util.Optional;
import java.time.LocalDateTime;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.fede.servicioubiynoti.entities.Prueba;


public interface PruebaRepository extends JpaRepository<Prueba, Integer> {
    @Query("SELECT p.empleado.legajo FROM Prueba p WHERE p.vehiculo.id = :idVehiculo AND p.fechaHoraInicio >= :fechaHora AND p.fechaHoraFin IS NULL")
    Optional<Integer> findLegajoEmpleadoPorPruebaActiva(@Param("idVehiculo") Integer idVehiculo, @Param("fechaHora") LocalDateTime fechaHora);
}

