package com.diego.sistemacontrolcomputadoras.controller;

import com.diego.sistemacontrolcomputadoras.model.Responsable;
import com.diego.sistemacontrolcomputadoras.repository.ResponsableRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/responsables")
public class ResponsableController {

    private final ResponsableRepository responsableRepository;

    public ResponsableController(ResponsableRepository responsableRepository) {
        this.responsableRepository = responsableRepository;
    }

    @GetMapping
    public String listarResponsables(Model model) {
        model.addAttribute("responsables", responsableRepository.findAll());
        return "responsables";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("responsable", new Responsable());
        return "responsable-form";
    }

    @PostMapping("/guardar")
    public String guardarResponsable(@Valid @ModelAttribute Responsable responsable,
                                     BindingResult result) {

        if (result.hasErrors()) {
            return "responsable-form";
        }

        responsableRepository.save(responsable);
        return "redirect:/responsables";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {

        Responsable responsable = responsableRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Responsable no encontrado"));

        model.addAttribute("responsable", responsable);
        return "responsable-form";
    }

    @PostMapping("/eliminar/{id}")
    public String eliminarResponsable(@PathVariable Long id,
                                      RedirectAttributes redirectAttributes) {
        try {
            responsableRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("mensajeExito", "Responsable eliminado correctamente.");
        } catch (DataIntegrityViolationException e) {
            redirectAttributes.addFlashAttribute("mensajeError",
                    "No se puede eliminar el responsable porque tiene computadoras asignadas.");
        }

        return "redirect:/responsables";
    }
}