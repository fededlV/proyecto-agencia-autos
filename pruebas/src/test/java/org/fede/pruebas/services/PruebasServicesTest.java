package org.fede.pruebas.services;

import org.fede.pruebas.entities.Vehiculo;
import org.fede.pruebas.repositories.PruebaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

class PruebasServicesTest {

    @InjectMocks
    private PruebasServices pruebasServices; //Servicio que se va a probar

    //Declaramos las depencdencias
    @Mock
    private PruebaRepository pruebaRepository; //Simulacion del repositorio
    @Mock
    private PruebasMapper pruebasMapper; //Simulacion del Mapper

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        //vehiculo = new Vehiculo()
    }
}