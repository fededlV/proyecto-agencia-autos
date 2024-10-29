package org.fede.pruebas.services;

import org.fede.pruebas.dto.InteresadoDto;
import org.fede.pruebas.entities.Interesado;
import org.springframework.stereotype.Service;

@Service
public class InteresadoMapper {

    public InteresadoDto toInteresadoDto(Interesado interesado) {
        return new InteresadoDto(
                interesado.getDocumento(),
                interesado.getTipoDoc(),
                interesado.getNombre(),
                interesado.getApellido(),
                interesado.getRestringido(),
                interesado.getNroLicencia(),
                interesado.getFechaVenLicencia()
        );
    }

    public Interesado toInteresado(InteresadoDto interesadoDto) {
        Interesado interesado = new Interesado();
        interesado.setDocumento(interesadoDto.documento());
        interesado.setTipoDoc(interesadoDto.tipo_documento());
        interesado.setNombre(interesadoDto.nombre());
        interesado.setApellido(interesadoDto.apellido());
        interesado.setRestringido(interesadoDto.restringido());
        interesado.setNroLicencia(interesadoDto.nro_licencia());
        interesado.setFechaVenLicencia(interesadoDto.fechaVencimientoLic());
        return interesado;
    }
}
