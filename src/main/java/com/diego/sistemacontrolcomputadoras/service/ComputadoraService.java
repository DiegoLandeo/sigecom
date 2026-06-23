package com.diego.sistemacontrolcomputadoras.service;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class ComputadoraService {

    public double calcularPorcentajeDocentesConInternet(long totalDocentes, long docentesConInternet) {

        if (totalDocentes == 0) {
            return 0.0;
        }

        return (docentesConInternet * 100.0) / totalDocentes;
    }

    public boolean esComputadoraSinDeteccionReciente(String ultimaDeteccion) {

        if (ultimaDeteccion == null || ultimaDeteccion.isBlank()) {
            return true;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime fechaDeteccion = LocalDateTime.parse(ultimaDeteccion, formatter);

        LocalDateTime fechaLimite = LocalDateTime.now().minusDays(7);

        return fechaDeteccion.isBefore(fechaLimite);
    }

    public String obtenerFechaLimiteSinDeteccion() {

        LocalDateTime fechaLimite = LocalDateTime.now().minusDays(7);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        return fechaLimite.format(formatter);
    }
}