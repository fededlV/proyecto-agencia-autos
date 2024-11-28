package org.fede.servicioubiynoti.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Manejar cuando no se encuentra un empleado
    @ExceptionHandler(EmpleadoNoEncontrado.class)
    public ResponseEntity<String> manejarEmpleadoNoEncontrado(EmpleadoNoEncontrado e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    // Manejar cuando no se encuentra un vehículo
    @ExceptionHandler(VehiculoNoEncontrado.class)
    public ResponseEntity<String> manejarVehiculoNoEncontrado(VehiculoNoEncontrado e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }
}
