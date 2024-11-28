package org.fede.servicioubiynoti.repositories;

import java.util.Optional;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.fede.servicioubiynoti.entities.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface EmpleadoRepository extends JpaRepository<Empleado, Integer> {
    Optional<Empleado> findByLegajo(Integer legajo);
    List<Empleado> findByTelefonoIn(List<Integer> telefonos);
}
