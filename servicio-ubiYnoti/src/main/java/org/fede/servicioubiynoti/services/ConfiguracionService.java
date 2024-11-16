package org.fede.servicioubiynoti.services;

import org.fede.servicioubiynoti.dto.PosicionDto;
import org.fede.servicioubiynoti.entities.Notificacion;
import org.fede.servicioubiynoti.repositories.NotificacionRepository;
import org.fede.servicioubiynoti.repositories.PruebaRepository;
import org.fede.servicioubiynoti.repositories.VehiculoRepository;
import org.springframework.stereotype.Service;

@Service
public class ValidacionService {

    private final PruebaRepository pruebaRepository;
    private final VehiculoRepository vehiculoRepository;
    private final NotificacionRepository notificacionRepository;

    public ValidacionService(PruebaRepository pruebaRepository,
                             VehiculoRepository vehiculoRepository,
                             NotificacionRepository notificacionRepository) {
        this.pruebaRepository = pruebaRepository;
        this.vehiculoRepository = vehiculoRepository;
        this.notificacionRepository = notificacionRepository;
    }

    public boolean validarPosicion(Integer vehiculoId, PosicionDto posicionDto) {
        // Verificar si el vehículo está en una prueba activa
        var pruebaActiva = pruebaRepository.findPruebaActivaByVehiculoId(vehiculoId);
        if (pruebaActiva == null) {
            return false;
        }

        // Validar si la posición está dentro del radio permitido
        if (!esPosicionPermitida(posicionDto)) {
            guardarNotificacion(vehiculoId, "El vehículo está fuera del radio permitido.");
            return false;
        }

        // Validar si la posición está en una zona restringida
        if (esZonaRestringida(posicionDto)) {
            guardarNotificacion(vehiculoId, "El vehículo ingresó a una zona restringida.");
            return false;
        }

        return true;
    }

    private boolean esPosicionPermitida(PosicionDto posicionDto) {
        // Implementar cálculo de distancia usando Haversine
        return true; // Aquí iría la lógica real
    }

    private boolean esZonaRestringida(PosicionDto posicionDto) {
        // Verificar si la posición está dentro de los límites de las zonas restringidas
        return false; // Aquí iría la lógica real
    }

    private void guardarNotificacion(Integer vehiculoId, String mensaje) {
        Notificacion notificacion = new Notificacion();
        notificacion.setMensaje(mensaje);
        notificacion.setEsIncidente(true);
        notificacion.setEmpleado(null); // Asociar con un empleado si aplica
        notificacionRepository.save(notificacion);
    }
}
