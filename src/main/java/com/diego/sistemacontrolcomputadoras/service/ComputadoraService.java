package com.diego.sistemacontrolcomputadoras.service;

import org.springframework.stereotype.Service;

@Service
public class ComputadoraService {

    public double calcularPorcentajeDocentesConInternet(long totalDocentes, long docentesConInternet) {

        if (totalDocentes == 0) {
            return 0;
        }

        return (docentesConInternet * 100.0) / totalDocentes;
    }
}
