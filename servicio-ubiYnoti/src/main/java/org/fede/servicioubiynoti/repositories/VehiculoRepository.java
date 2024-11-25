package org.fede.servicioubiynoti.repositories;

import org.fede.servicioubiynoti.entities.Vehiculo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehiculoRepository extends JpaRepository<Vehiculo, Integer> {
    // Métodos personalizados si son necesarios
}
