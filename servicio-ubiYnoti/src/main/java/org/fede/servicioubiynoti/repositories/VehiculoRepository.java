package org.fede.servicioubiynoti.repositories;

import org.fede.servicioubiynoti.entities.Vehiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;

public interface VehiculoRepository extends JpaRepository<Vehiculo, Integer> {
    @Query("SELECT p.vehiculo.id FROM Prueba p WHERE p.vehiculo.id = :idVehiculo")
    Optional<Integer> findIdByVehiculoId(@Param("idVehiculo") Integer idVehiculo);
}
