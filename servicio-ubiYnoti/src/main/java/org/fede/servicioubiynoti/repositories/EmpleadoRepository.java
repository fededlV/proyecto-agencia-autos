package org.fede.servicioubiynoti.repositories;

import org.fede.servicioubiynoti.entities.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmpleadoRepository extends JpaRepository<Empleado, Integer> {
    Optional<Empleado> findByLegajo(Integer legajo);
}
