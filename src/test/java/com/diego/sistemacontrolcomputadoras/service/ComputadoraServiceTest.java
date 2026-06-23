package com.diego.sistemacontrolcomputadoras.service;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

class ComputadoraServiceTest {

    private final ComputadoraService computadoraService = new ComputadoraService();

    @Test
    void calcularPorcentajeDocentesConInternet_cuandoExistenDocentes_retornaPorcentajeCorrecto() {

        double resultado = computadoraService.calcularPorcentajeDocentesConInternet(4, 3);

        assertEquals(75.0, resultado);
    }

    @Test
    void calcularPorcentajeDocentesConInternet_cuandoNoExistenDocentes_retornaCero() {

        double resultado = computadoraService.calcularPorcentajeDocentesConInternet(0, 0);

        assertEquals(0.0, resultado);
    }

    @Test
    void esComputadoraSinDeteccionReciente_cuandoFechaEsAntigua_retornaVerdadero() {

        String fechaAntigua = LocalDateTime.now()
                .minusDays(10)
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        boolean resultado = computadoraService.esComputadoraSinDeteccionReciente(fechaAntigua);

        assertTrue(resultado);
    }

    @Test
    void esComputadoraSinDeteccionReciente_cuandoFechaEsReciente_retornaFalso() {

        String fechaReciente = LocalDateTime.now()
                .minusDays(2)
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        boolean resultado = computadoraService.esComputadoraSinDeteccionReciente(fechaReciente);

        assertFalse(resultado);
    }

    @Test
    void obtenerFechaLimiteSinDeteccion_retornaFechaConFormatoCorrecto() {

        String fechaLimite = computadoraService.obtenerFechaLimiteSinDeteccion();

        assertNotNull(fechaLimite);
        assertDoesNotThrow(() ->
                LocalDateTime.parse(fechaLimite, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        );
    }
}