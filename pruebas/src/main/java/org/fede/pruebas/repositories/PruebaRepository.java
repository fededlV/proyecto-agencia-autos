package org.fede.pruebas.repositories;

import org.fede.pruebas.entities.Pruebas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PruebaRepository extends JpaRepository<Pruebas, Integer> {
}
