package org.fede.servicioreportes.controllers;

import org.apache.catalina.LifecycleState;
import org.fede.servicioreportes.dto.IncidenteDTO;
import org.fede.servicioreportes.dto.PruebaResponseDTO;
import org.fede.servicioreportes.services.ReporteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/reportes")
public class ReporteController {

    @Autowired
    private ReporteService reporteService;

    // Endpoint para obtener todos los incidentes
    @GetMapping("/incidentes")
    public ResponseEntity<List<IncidenteDTO>> obtenerReporteDeIncidentes() {
        List<IncidenteDTO> incidentes = reporteService.generarReporteIncidentes();
        return ResponseEntity.ok(incidentes);
    }

    // Endpoint para obtener la cantidad de kilometros de prueba
    @GetMapping("/cantidadKilometros")
    public ResponseEntity<Double> calcularCantidadKilometros(
            @RequestParam Integer vehiculoId,
            @RequestParam LocalDateTime fechaInicio,
            @RequestParam LocalDateTime fechaFin
            ) {
        double kilometros = reporteService.calcularKilometrosRecorridos(vehiculoId, fechaInicio, fechaFin);
        return ResponseEntity.ok(kilometros); //Devuelve la cantidad de kilometros recorridos.
    }

    //Enpoint para pruebas por vehiculo
    @GetMapping("/pruebasVehiculo")
    public ResponseEntity<List<PruebaResponseDTO>> obtenerPruebasPorVehiculo(
            @RequestParam Integer vehiculoId
    ) {
        List<PruebaResponseDTO> pruebas = reporteService.obtenerPruebasPorVehiculo(vehiculoId);
        return ResponseEntity.ok(pruebas); //Devuelve la lista de pruebas realizadas
    }



}
