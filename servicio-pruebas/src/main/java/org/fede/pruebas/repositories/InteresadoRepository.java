package org.fede.pruebas.repositories;

import org.fede.pruebas.entities.Interesado;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InteresadoRepository extends JpaRepository<Interesado, Integer> {
}
