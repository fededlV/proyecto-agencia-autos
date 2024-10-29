package org.fede.pruebas.repositories;

import org.fede.pruebas.entities.Posicion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PosicionRepository extends JpaRepository<Posicion, Integer> {
}
