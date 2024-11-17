package org.fede.servicioubiynoti.repositories;

import org.fede.servicioubiynoti.entities.Interesado;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InteresadoRepository extends JpaRepository<Interesado, Integer> {
}
