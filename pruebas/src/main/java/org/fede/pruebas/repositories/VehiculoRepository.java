package org.fede.pruebas.repositories;


import org.fede.pruebas.entities.Vehiculo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VehiculoRepository extends JpaRepository<Vehiculo, Integer> {

    Optional<Vehiculo> findByPatente(String patente);
}
