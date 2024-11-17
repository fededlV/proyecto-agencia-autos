package org.fede.servicioubiynoti.repositories;

import org.fede.servicioubiynoti.entities.Notificacion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificacionRepository extends JpaRepository<Notificacion, Integer> {
}
