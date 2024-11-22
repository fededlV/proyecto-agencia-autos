package org.fede.pruebas.services;

import jakarta.persistence.EntityNotFoundException;
import org.fede.pruebas.dto.VehiculoDto;
import org.fede.pruebas.dto.VehiculoResponseDto;
import org.fede.pruebas.entities.Modelo;
import org.fede.pruebas.entities.Vehiculo;
import org.fede.pruebas.repositories.ModeloRepository;
import org.fede.pruebas.repositories.VehiculoRepository;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class VehiculoService {

    private final VehiculoRepository vehiculoRepository;
    private final VehiculoMapper vehiculoMapper;
    private final ModeloRepository modeloRepository;

    public VehiculoService(VehiculoRepository vehiculoRepository, VehiculoMapper vehiculoMapper, ModeloRepository modeloRepository, MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter) {
        this.vehiculoRepository = vehiculoRepository;
        this.vehiculoMapper = vehiculoMapper;
        this.modeloRepository = modeloRepository;
    }

    public VehiculoResponseDto createVehiculo(VehiculoDto vehiculoDto) {
        Vehiculo vehiculo = vehiculoMapper.toVehiculo(vehiculoDto);
        Vehiculo vehiculoSaved = vehiculoRepository.save(vehiculo);
        return vehiculoMapper.toVehiculoResponseDto(vehiculoSaved);
    }

    public List<VehiculoResponseDto> findAll() {
        return vehiculoRepository.findAll()
                .stream()
                .map(vehiculoMapper::toVehiculoResponseDto)
                .collect(Collectors.toList());
    }

    public VehiculoResponseDto updateVehiculo(Integer id, VehiculoDto dto) {
        //Busqueda de las entidades en la base de datos.
        Vehiculo vehiculo = vehiculoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("El vehiculo no se encontro"));
        Modelo modelo = modeloRepository.findById(dto.id_modelo())
                .orElseThrow(() -> new EntityNotFoundException("El modelo no se encontro"));

        //Actualiza los datos
        vehiculo.setModelo(modelo);
        vehiculo.setPatente(dto.patente());

        //Retorna el response DTO de vehiculo, y lo guarda en la base de datos al vehiculo actualizado
        return vehiculoMapper.toVehiculoResponseDto(vehiculoRepository.save(vehiculo));
    }

    public void deleteVehiculo(Integer id) {
        vehiculoRepository.deleteById(id);
    }
}
