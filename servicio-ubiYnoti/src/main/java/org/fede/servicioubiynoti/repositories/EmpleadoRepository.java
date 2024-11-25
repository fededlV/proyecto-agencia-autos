package org.fede.servicioubiynoti.repositories;

import java.util.Optional;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.fede.servicioubiynoti.entities.Empleado;

public interface EmpleadoRepository extends JpaRepository<Empleado, Integer> {
    Optional<Empleado> findByLegajo(Integer legajo);
}
