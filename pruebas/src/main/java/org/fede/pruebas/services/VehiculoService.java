package org.fede.pruebas.services;

import org.fede.pruebas.dto.VehiculoDto;
import org.fede.pruebas.repositories.VehiculoRepository;
import org.springframework.stereotype.Service;


@Service
public class VehiculoService {

    private final VehiculoRepository vehiculoRepository;
    private final VehiculoMapper vehiculoMapper;

    public VehiculoService(VehiculoRepository vehiculoRepository, VehiculoMapper vehiculoMapper) {
        this.vehiculoRepository = vehiculoRepository;
        this.vehiculoMapper = vehiculoMapper;
    }

    public void createVehiculo(VehiculoDto vehiculoDto) {
        var vehiculo = vehiculoMapper.toVehiculo(vehiculoDto);
        vehiculoRepository.save(vehiculo);
    }
}
