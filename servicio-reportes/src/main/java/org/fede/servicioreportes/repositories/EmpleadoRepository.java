package org.fede.servicioreportes.repositories;

import org.fede.servicioreportes.entities.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpleadoRepository extends JpaRepository<Empleado, Integer> {
}
