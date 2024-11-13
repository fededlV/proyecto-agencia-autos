package org.fede.servicioreportes.controllers;

import org.fede.servicioreportes.dto.IncidenteDTO;
import org.fede.servicioreportes.dto.PruebaDTO;
import org.fede.servicioreportes.entities.Prueba;
import org.fede.servicioreportes.repositories.PruebaRepository;
import org.fede.servicioreportes.services.ReporteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/reportes")
public class ReporteController {

    @Autowired
    private ReporteService reporteService;

    // Endpoint para obtener todos los incidentes
    @GetMapping("/incidentes")
    public ResponseEntity<List<IncidenteDTO>> obtenerIncidentes() {
        List<IncidenteDTO> incidentes = reporteService.obtenerIncidentes();
        return ResponseEntity.ok(incidentes);  // Devolver IncidenteDTO, no Prueba
    }

    /*
    // Endpoint para obtener incidentes por empleado
    @GetMapping("/incidentes/{empleadoId}")
    public ResponseEntity<List<IncidenteDTO>> obtenerIncidentesPorEmpleado(@PathVariable Long empleadoId) {
        List<IncidenteDTO> incidentes = reporteService.obtenerIncidentesPorEmpleado(empleadoId);
        return ResponseEntity.ok(incidentes);  // Devolver IncidenteDTO
    }

    // Endpoint para calcular kilómetros recorridos por un vehículo en un período
    @GetMapping("/kilometros/{vehiculoId}")
    public ResponseEntity<Double> calcularKilometrosRecorridos(@PathVariable Long vehiculoId,
                                                               @RequestParam("fechaInicio") String fechaInicio,
                                                               @RequestParam("fechaFin") String fechaFin) {
        // Llamar al servicio para calcular los kilómetros recorridos
        double kilometros = reporteService.calcularKilometrosRecorridos(vehiculoId, fechaInicio, fechaFin);
        return ResponseEntity.ok(kilometros);
    }

    @GetMapping("/pruebas/{vehiculoId}")
    public ResponseEntity<List<Prueba>> obtenerPruebasPorVehiculo(@PathVariable Long vehiculoId) {
        List<Prueba> pruebas = reporteService.obtenerPruebasPorVehiculo(vehiculoId);
        return ResponseEntity.ok(pruebas);
    }
    */


}