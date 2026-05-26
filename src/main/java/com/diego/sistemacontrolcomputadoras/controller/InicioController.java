package com.diego.sistemacontrolcomputadoras.controller;

import com.diego.sistemacontrolcomputadoras.repository.ComputadoraRepository;
import com.diego.sistemacontrolcomputadoras.repository.ResponsableRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
public class InicioController {

    private final ComputadoraRepository computadoraRepository;
    private final ResponsableRepository responsableRepository;

    public InicioController(ComputadoraRepository computadoraRepository,
                            ResponsableRepository responsableRepository) {
        this.computadoraRepository = computadoraRepository;
        this.responsableRepository = responsableRepository;
    }

    @GetMapping("/")
    public String inicio(Model model) {

        long totalComputadoras = computadoraRepository.count();
        long totalResponsables = responsableRepository.count();
        long conInternet = computadoraRepository.countByTieneInternet(true);
        long sinInternet = computadoraRepository.countByTieneInternet(false);
        long pendientes = computadoraRepository.countByEstado("Pendiente de completar");

        model.addAttribute("totalComputadoras", totalComputadoras);
        model.addAttribute("totalResponsables", totalResponsables);
        model.addAttribute("conInternet", conInternet);
        model.addAttribute("sinInternet", sinInternet);
        model.addAttribute("pendientes", pendientes);

        model.addAttribute("ultimasComputadoras", computadoraRepository.findTop5ByOrderByIdDesc());

        LocalDateTime fechaLimite = LocalDateTime.now().minusDays(7);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String fechaLimiteTexto = fechaLimite.format(formatter);

        long sinDeteccion = computadoraRepository.findByUltimaDeteccionLessThan(fechaLimiteTexto).size()
                + computadoraRepository.findByUltimaDeteccionIsNull().size();

        model.addAttribute("sinDeteccion", sinDeteccion);

        return "index";
    }
}
