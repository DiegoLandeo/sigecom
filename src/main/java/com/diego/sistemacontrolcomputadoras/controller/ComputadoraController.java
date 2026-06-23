package com.diego.sistemacontrolcomputadoras.controller;

import com.diego.sistemacontrolcomputadoras.model.Computadora;
import com.diego.sistemacontrolcomputadoras.repository.ComputadoraRepository;
import com.diego.sistemacontrolcomputadoras.repository.ResponsableRepository;
import com.diego.sistemacontrolcomputadoras.service.ComputadoraService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.dao.DataIntegrityViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/computadoras")
public class ComputadoraController {

    private final ComputadoraRepository computadoraRepository;
    private final ResponsableRepository responsableRepository;
    private final ComputadoraService computadoraService;

    public ComputadoraController(ComputadoraRepository computadoraRepository,
                                 ResponsableRepository responsableRepository,
                                 ComputadoraService computadoraService) {
        this.computadoraRepository = computadoraRepository;
        this.responsableRepository = responsableRepository;
        this.computadoraService = computadoraService;
    }

    @GetMapping
    public String listarComputadoras(Model model) {
        model.addAttribute("computadoras", computadoraRepository.findAll());
        return "computadoras";
    }

    @GetMapping("/con-internet")
    public String listarConInternet(Model model) {
        model.addAttribute("computadoras", computadoraRepository.findByTieneInternet(true));
        return "computadoras";
    }

    @GetMapping("/sin-internet")
    public String listarSinInternet(Model model) {
        model.addAttribute("computadoras", computadoraRepository.findByTieneInternet(false));
        return "computadoras";
    }

    @GetMapping("/nueva")
    public String mostrarFormularioNueva(Model model) {
        model.addAttribute("computadora", new Computadora());
        model.addAttribute("responsables", responsableRepository.findAll());
        model.addAttribute("horarios", generarHorarios());
        return "computadora-form";
    }

    @PostMapping("/guardar")
    public String guardarComputadora(@Valid @ModelAttribute Computadora computadora,
                                     BindingResult result,
                                     Model model,
                                     RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            model.addAttribute("responsables", responsableRepository.findAll());
            model.addAttribute("horarios", generarHorarios());
            return "computadora-form";
        }

        boolean esNueva = computadora.getId() == null;

        if (computadora.getEstado() == null || computadora.getEstado().isBlank()) {
            computadora.setEstado("Activa");
        }

        computadoraRepository.save(computadora);

        if (esNueva) {
            redirectAttributes.addFlashAttribute("mensajeExito", "Computadora registrada correctamente.");
        } else {
            redirectAttributes.addFlashAttribute("mensajeExito", "Computadora actualizada correctamente.");
        }

        return "redirect:/computadoras";
    }

    @GetMapping("/reporte-almacenamiento")
    public String reporteAlmacenamiento(Model model) {
        model.addAttribute("reporteAlmacenamiento", computadoraRepository.obtenerAlmacenamientoPorLocal());
        return "reporte-almacenamiento";
    }

    @GetMapping("/reporte-docentes-internet")
    public String reporteDocentesInternet(Model model) {

        long totalDocentes = computadoraRepository.countByAmbienteDocente(true);
        long docentesConInternet = computadoraRepository.countByAmbienteDocenteAndTieneInternet(true, true);

        double porcentaje = computadoraService.calcularPorcentajeDocentesConInternet(
                totalDocentes,
                docentesConInternet
        );

        model.addAttribute("totalDocentes", totalDocentes);
        model.addAttribute("docentesConInternet", docentesConInternet);
        model.addAttribute("porcentaje", porcentaje);

        return "reporte-docentes-internet";
    }

    @GetMapping("/reporte-horario")
    public String mostrarReporteHorario(Model model) {
        model.addAttribute("horarios", generarHorarios());
        return "reporte-horario";
    }

    @PostMapping("/reporte-horario")
    public String consultarReporteHorario(@RequestParam String hora, Model model) {
        model.addAttribute("computadoras", computadoraRepository.buscarComputadorasPorHorario(hora));
        model.addAttribute("horaConsultada", hora);
        model.addAttribute("horarios", generarHorarios());
        return "reporte-horario";
    }

    @GetMapping("/reporte-docentes-responsable")
    public String mostrarReporteDocentesResponsable(Model model) {
        model.addAttribute("responsables", responsableRepository.findAll());
        model.addAttribute("computadoras", null);
        model.addAttribute("responsableSeleccionado", null);
        return "reporte-docentes-responsable";
    }

    @GetMapping("/completar/{id}")
    public String mostrarFormularioCompletar(@PathVariable Long id, Model model) {

        Computadora computadora = computadoraRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Computadora no encontrada"));

        model.addAttribute("computadora", computadora);
        model.addAttribute("responsables", responsableRepository.findAll());
        model.addAttribute("horarios", generarHorarios());

        return "completar-computadora";
    }

    @PostMapping("/completar")
    public String completarComputadora(@ModelAttribute Computadora computadora,
                                       RedirectAttributes redirectAttributes) {

        Computadora computadoraExistente = computadoraRepository.findById(computadora.getId())
                .orElseThrow(() -> new IllegalArgumentException("Computadora no encontrada"));

        computadoraExistente.setLocal(computadora.getLocal());
        computadoraExistente.setResponsable(computadora.getResponsable());
        computadoraExistente.setAmbienteDocente(computadora.getAmbienteDocente());
        computadoraExistente.setTieneInternet(computadora.getTieneInternet());
        computadoraExistente.setHoraInicioNavegacion(computadora.getHoraInicioNavegacion());
        computadoraExistente.setHoraFinNavegacion(computadora.getHoraFinNavegacion());
        computadoraExistente.setEstado("Activa");

        computadoraRepository.save(computadoraExistente);

        redirectAttributes.addFlashAttribute("mensajeExito", "Datos administrativos completados correctamente.");

        return "redirect:/computadoras";
    }

    @PostMapping("/reporte-docentes-responsable")
    public String consultarReporteDocentesResponsable(@RequestParam Long responsableId, Model model) {

        model.addAttribute("responsables", responsableRepository.findAll());

        model.addAttribute("computadoras",
                computadoraRepository.findByResponsableIdAndAmbienteDocenteAndTieneInternet(
                        responsableId,
                        true,
                        true
                )
        );

        model.addAttribute("responsableSeleccionado", responsableId);

        return "reporte-docentes-responsable";
    }

    @PostMapping("/pre-registrar")
    @ResponseBody
    public String preRegistrarComputadora(@RequestBody Computadora computadora) {

        return computadoraRepository.findByIp(computadora.getIp())
                .map(computadoraExistente -> {

                    computadoraExistente.setNombreEquipo(computadora.getNombreEquipo());
                    computadoraExistente.setMac(computadora.getMac());
                    computadoraExistente.setProcesador(computadora.getProcesador());
                    computadoraExistente.setRam(computadora.getRam());
                    computadoraExistente.setDiscoDuro(computadora.getDiscoDuro());
                    computadoraExistente.setDiscoLibre(computadora.getDiscoLibre());
                    computadoraExistente.setSistemaOperativo(computadora.getSistemaOperativo());
                    computadoraExistente.setUltimaDeteccion(computadora.getUltimaDeteccion());
                    computadoraExistente.setTieneInternet(computadora.getTieneInternet());

                    computadoraRepository.save(computadoraExistente);

                    return "Datos técnicos actualizados correctamente";

                })
                .orElseGet(() -> {

                    computadora.setEstado("Pendiente de completar");

                    computadoraRepository.save(computadora);

                    return "Computadora pre-registrada correctamente";
                });
    }

    @Transactional
    @PostMapping("/api/eliminar-por-ip")
    @ResponseBody
    public String eliminarComputadoraPorIpApi(@RequestParam String ip) {

        return computadoraRepository.findByIp(ip)
                .map(computadora -> {
                    computadoraRepository.delete(computadora);
                    return "Computadora eliminada correctamente";
                })
                .orElse("No se encontró una computadora con esa IP");
    }

    @GetMapping("/pendientes")
    public String listarPendientes(Model model) {
        model.addAttribute("computadoras", computadoraRepository.findByEstado("Pendiente de completar"));
        return "computadoras";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditarComputadora(@PathVariable Long id, Model model) {

        Computadora computadora = computadoraRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Computadora no encontrada"));

        model.addAttribute("computadora", computadora);
        model.addAttribute("responsables", responsableRepository.findAll());
        model.addAttribute("horarios", generarHorarios());

        return "computadora-form";
    }

    @PostMapping("/eliminar/{id}")
    public String eliminarComputadora(@PathVariable Long id,
                                      RedirectAttributes redirectAttributes) {
        try {
            computadoraRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("mensajeExito", "Computadora eliminada correctamente.");
        } catch (DataIntegrityViolationException e) {
            redirectAttributes.addFlashAttribute("mensajeError", "No se pudo eliminar la computadora porque tiene información relacionada.");
        }

        return "redirect:/computadoras";
    }

    @GetMapping("/buscar")
    public String buscarComputadoras(@RequestParam String texto, Model model) {

        model.addAttribute(
                "computadoras",
                computadoraRepository
                        .findByNombreEquipoContainingIgnoreCaseOrIpContainingIgnoreCase(texto, texto)
        );

        model.addAttribute("textoBusqueda", texto);

        return "computadoras";
    }

    @GetMapping("/reporte-sin-deteccion")
    public String reporteSinDeteccion(Model model) {

        String fechaLimiteTexto = computadoraService.obtenerFechaLimiteSinDeteccion();

        List<Computadora> computadorasSinDeteccion = new ArrayList<>();
        computadorasSinDeteccion.addAll(computadoraRepository.findByUltimaDeteccionLessThan(fechaLimiteTexto));
        computadorasSinDeteccion.addAll(computadoraRepository.findByUltimaDeteccionIsNull());

        model.addAttribute("computadoras", computadorasSinDeteccion);
        model.addAttribute("fechaLimite", fechaLimiteTexto);

        return "reporte-sin-deteccion";
    }

    private List<String> generarHorarios() {
        return IntStream.range(0, 48)
                .mapToObj(i -> String.format("%02d:%02d", i / 2, (i % 2) * 30))
                .collect(Collectors.toList());
    }
}
