package org.fede.pruebas.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmpleadoNoEncontradoException.class)
    public ResponseEntity<String> manejarEmpleadoNoEncontrado(EmpleadoNoEncontradoException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(LicenciaVencidaException.class)
    public ResponseEntity<String> manejarLicenciaVencida(LicenciaVencidaException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InteresadoNoEncontradoException.class)
    public ResponseEntity<String> manejarInteresadoNoEcontrado(InteresadoNoEncontradoException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InteresadoRestringidoException.class)
    public ResponseEntity<String> manejarInteresadoRestringido(InteresadoRestringidoException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(VehiculoEnPruebaException.class)
    public ResponseEntity<String> manejarVehiculoEnPrueba(VehiculoEnPruebaException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(VehiculoNoEncontradoException.class)
    public ResponseEntity<String> manejarVehiculoNoEncontrado(VehiculoNoEncontradoException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PruebaNoEncontradaException.class)
    public ResponseEntity<String> manejarPruebaNoEncontrada(PruebaNoEncontradaException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PruebaFinalizadaException.class)
    public ResponseEntity<String> manejarPruebaFinalizada(PruebaFinalizadaException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SinPruebasEnCursoException.class)
    public ResponseEntity<String> manejarSinPruebasEnCurso(SinPruebasEnCursoException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RolNoHabilitado.class)
    public ResponseEntity<String> manenjarRolNoHabilitado(RolNoHabilitado e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
