package org.fede.servicioreportes.repositories;

import org.fede.servicioreportes.entities.Posicion;
import org.fede.servicioreportes.entities.Prueba;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface PruebaRepository extends JpaRepository<Prueba, Long> {
    @Query("SELECT p FROM Prueba p WHERE p.fechaHoraFin IS NULL")
    List<Prueba> findPruebasEnCurso();
    /*
    @Query("SELECT p FROM Prueba p WHERE p.excedeLimite = true AND p.empleado.id = :empleadoId")
    List<Prueba> findIncidentesPorEmpleado(Long empleadoId);

    List<Prueba> findByVehiculoId(Long vehiculoId);
    */

    List<Prueba> findPruebaByVehiculo(Integer vehiculoId);
}
