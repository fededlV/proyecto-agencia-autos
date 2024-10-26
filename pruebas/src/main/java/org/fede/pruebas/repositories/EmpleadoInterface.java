package org.fede.pruebas.repositories;

import org.fede.pruebas.entities.Empleados;
import org.springframework.data.jpa.repository.JpaRepository;


public interface EmpleadoInterface extends JpaRepository<Empleados, Integer> {
}
