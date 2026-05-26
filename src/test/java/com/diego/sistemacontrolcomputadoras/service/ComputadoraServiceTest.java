package com.diego.sistemacontrolcomputadoras.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
}
