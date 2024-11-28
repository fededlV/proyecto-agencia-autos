package org.fede.servicioubiynoti.services;

import org.fede.servicioubiynoti.dto.PromocionRequestDTO;
import org.fede.servicioubiynoti.entities.Empleado;
import org.fede.servicioubiynoti.entities.Notificacion;
import org.fede.servicioubiynoti.repositories.EmpleadoRepository;
import org.fede.servicioubiynoti.repositories.NotificacionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PromocionService {

    private final EmpleadoRepository empleadoRepository;
    private final NotificacionRepository notificacionRepository;

    public PromocionService(EmpleadoRepository empleadoRepository, NotificacionRepository notificacionRepository) {
        this.empleadoRepository = empleadoRepository;
        this.notificacionRepository = notificacionRepository;
    }

    public boolean enviarPromociones(PromocionRequestDTO promocionRequestDTO) {

        List<Empleado> empleados = empleadoRepository.findByTelefonoIn(promocionRequestDTO.getTelefonos());

        if (empleados.isEmpty()) {
            return false;
        }

        for (Empleado empleado : empleados) {
            Notificacion notificacion = new Notificacion(
                    empleado.getLegajo(),
                    promocionRequestDTO.getMensaje(),
                    LocalDateTime.now(),
                    false
            );
            notificacionRepository.save(notificacion);
        }
        return true;
    }
}
