package org.fede.servicioubiynoti.repositories;

import org.fede.servicioubiynoti.entities.Notificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificacionRepository extends JpaRepository<Notificacion, Integer> {
}
