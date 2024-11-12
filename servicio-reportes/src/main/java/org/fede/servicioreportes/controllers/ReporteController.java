package org.fede.servicioreportes.controllers;

import org.fede.servicioreportes.dto.PruebaDTO;
import org.fede.servicioreportes.entities.Prueba;
import org.fede.servicioreportes.repositories.PruebaRepository;
import org.fede.servicioreportes.services.ReporteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reportes")
public class ReporteController {

    @Autowired
    private ReporteService reporteService;

    @GetMapping("/incidentes")
    public ResponseEntity<List<Prueba>> obtenerIncidentes() {
        List<Prueba> incidentes = reporteService.obtenerIncidentes();
        return ResponseEntity.ok(incidentes);
    }

    @GetMapping("/incidentes/{empleadoId}")
    public ResponseEntity<List<Prueba>> obtenerIncidentesPorEmpleado(@PathVariable Long empleadoId) {
        List<Prueba> incidentes = reporteService.obtenerIncidentesPorEmpleado(empleadoId);
        return ResponseEntity.ok(incidentes);
    }

    @GetMapping("/kilometros/{vehiculoId}")
    public ResponseEntity<Double> calcularKilometrosRecorridos(@PathVariable Long vehiculoId,
                                                               @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
                                                               @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin) {
        double kilometros = reporteService.calcularKilometrosRecorridos(vehiculoId, fechaInicio, fechaFin);
        return ResponseEntity.ok(kilometros);
    }

    @GetMapping("/pruebas/{vehiculoId}")
    public ResponseEntity<List<Prueba>> obtenerPruebasPorVehiculo(@PathVariable Long vehiculoId) {
        List<Prueba> pruebas = reporteService.obtenerPruebasPorVehiculo(vehiculoId);
        return ResponseEntity.ok(pruebas);
    }


}
