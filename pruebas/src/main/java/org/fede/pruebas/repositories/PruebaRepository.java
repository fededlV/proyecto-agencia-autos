package org.fede.pruebas.repositories;

import org.fede.pruebas.entities.Prueba;
import org.fede.pruebas.entities.Vehiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.time.LocalDateTime;
import java.util.List;

public interface PruebaRepository extends JpaRepository<Prueba, Integer> {

    boolean existsByVehiculoAndFechaHoraInicioLessThanEqualAndFechaHoraFinGreaterThanEqual(
            Vehiculo vehiculo, LocalDateTime fecha_hora_inicio, LocalDateTime fecha_hora_fin
    );

    //Query que realiza la busqueda en la base de datos y devuelve las pruebas que cumplen con esas condiciones, Query JPQL
    @Query("SELECT p FROM Prueba p WHERE :fechaHora BETWEEN p.fechaHoraInicio AND p.fechaHoraFin")
    List<Prueba> findByPruebasEnCurso(@Param("fechaHora")LocalDateTime fechaHora);
}
