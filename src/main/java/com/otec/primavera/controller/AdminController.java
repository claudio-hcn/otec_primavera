package com.otec.primavera.controller;

import com.otec.primavera.dto.UsuarioRegistroDTO;
import com.otec.primavera.model.Hito;
import com.otec.primavera.model.Matricula;
import com.otec.primavera.model.Modulo;
import com.otec.primavera.model.Programa;
import com.otec.primavera.model.Usuario;
import com.otec.primavera.repository.ModuloRepository;
import com.otec.primavera.service.HitoService;
import com.otec.primavera.service.MatriculaService;
import com.otec.primavera.service.ProgramaService;
import com.otec.primavera.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ProgramaService programaService;

    @Autowired
    private MatriculaService matriculaService;

    @Autowired
    private HitoService hitoService;

    @Autowired
    private ModuloRepository moduloRepository;

    // ─── DASHBOARD ───────────────────────────────────────────
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("programas", programaService.findAll());
        model.addAttribute("estudiantes", usuarioService.findAllEstudiantes());
        model.addAttribute("matriculas", matriculaService.findAll());
        return "admin/dashboard";
    }

    // ─── PROGRAMAS ────────────────────────────────────────────
    @GetMapping("/programas")
    public String listarProgramas(Model model) {
        model.addAttribute("programas", programaService.findAll());
        model.addAttribute("programa", new Programa());
        return "admin/programas";
    }

    @PostMapping("/programas/guardar")
    public String guardarPrograma(@ModelAttribute Programa programa) {
        programaService.guardar(programa);
        return "redirect:/admin/programas";
    }

    @GetMapping("/programas/editar/{id}")
    public String editarPrograma(@PathVariable Long id, Model model) {
        programaService.findById(id).ifPresent(p -> model.addAttribute("programa", p));
        model.addAttribute("programas", programaService.findAll());
        return "admin/programas";
    }

    @PostMapping("/programas/actualizar")
    public String actualizarPrograma(@ModelAttribute Programa programa) {
        programaService.actualizar(programa);
        return "redirect:/admin/programas";
    }

    @GetMapping("/programas/eliminar/{id}")
    public String eliminarPrograma(@PathVariable Long id) {
        programaService.eliminar(id);
        return "redirect:/admin/programas";
    }

    // ─── MÓDULOS ──────────────────────────────────────────────
    @GetMapping("/programas/{id}/modulos")
    public String verModulos(@PathVariable Long id, Model model) {
        programaService.findById(id).ifPresent(p -> {
            model.addAttribute("programa", p);
            model.addAttribute("modulos",
                    moduloRepository.findByProgramaIdOrderByOrden(id));
            model.addAttribute("nuevoModulo", new Modulo());
        });
        return "admin/modulos";
    }

    @PostMapping("/programas/{id}/modulos/guardar")
    public String guardarModulo(@PathVariable Long id,
                                @ModelAttribute Modulo modulo) {
        programaService.findById(id).ifPresent(p -> {
            modulo.setPrograma(p);
            moduloRepository.save(modulo);
        });
        return "redirect:/admin/programas/" + id + "/modulos";
    }

    @GetMapping("/programas/{id}/modulos/eliminar/{moduloId}")
    public String eliminarModulo(@PathVariable Long id,
                                 @PathVariable Long moduloId) {
        moduloRepository.deleteById(moduloId);
        return "redirect:/admin/programas/" + id + "/modulos";
    }

    // ─── ESTUDIANTES ──────────────────────────────────────────
    @GetMapping("/estudiantes")
    public String listarEstudiantes(Model model) {
        model.addAttribute("estudiantes", usuarioService.findAllEstudiantes());
        model.addAttribute("usuarioDTO", new UsuarioRegistroDTO());
        return "admin/estudiantes";
    }

    @PostMapping("/estudiantes/guardar")
    public String guardarEstudiante(@ModelAttribute UsuarioRegistroDTO dto) {
        dto.setRol(Usuario.Rol.ESTUDIANTE);
        usuarioService.registrar(dto);
        return "redirect:/admin/estudiantes";
    }

    // ─── MATRÍCULAS ───────────────────────────────────────────
    @GetMapping("/matriculas")
    public String listarMatriculas(Model model) {
        model.addAttribute("matriculas", matriculaService.findAll());
        model.addAttribute("estudiantes", usuarioService.findAllEstudiantes());
        model.addAttribute("programas",
                programaService.findByEstado(Programa.Estado.ACTIVO));
        return "admin/matriculas";
    }

    @PostMapping("/matriculas/guardar")
    public String guardarMatricula(@RequestParam Long estudianteId,
                                   @RequestParam Long programaId) {
        Usuario estudiante = usuarioService.findById(estudianteId)
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));
        Programa programa = programaService.findById(programaId)
                .orElseThrow(() -> new RuntimeException("Programa no encontrado"));
        matriculaService.matricular(estudiante, programa);
        return "redirect:/admin/matriculas";
    }

    // ─── HITOS ────────────────────────────────────────────────
    @GetMapping("/hitos/{matriculaId}")
    public String verHitos(@PathVariable Long matriculaId, Model model) {
        cargarModeloHitos(matriculaId, model);
        return "admin/hitos";
    }

  @PostMapping("/hitos/guardar")
public String guardarHito(@RequestParam Long matriculaId,
                          @RequestParam Hito.Tipo tipo,
                          @RequestParam Long moduloId,
                          @RequestParam(required = false) Double nota,
                          @RequestParam(required = false) String observacion,
                          Model model) {

    // Validar módulo ya cerrado para MODULO_COMPLETADO
    if (tipo == Hito.Tipo.MODULO_COMPLETADO) {
        if (hitoService.moduloYaCompletado(matriculaId, moduloId)) {
            cargarModeloHitos(matriculaId, model);
            model.addAttribute("error", "Este módulo ya fue cerrado.");
            return "admin/hitos";
        }
        double promedio = hitoService.calcularPromedioModulo(matriculaId, moduloId);
        if (promedio == 0.0) {
            cargarModeloHitos(matriculaId, model);
            model.addAttribute("error",
                    "No hay evaluaciones registradas para este módulo.");
            return "admin/hitos";
        }
        Matricula matricula = matriculaService.findById(matriculaId)
                .orElseThrow(() -> new RuntimeException("Matrícula no encontrada"));
        Modulo modulo = moduloRepository.findById(moduloId)
                .orElseThrow(() -> new RuntimeException("Módulo no encontrado"));
        Hito hito = new Hito();
        hito.setMatricula(matricula);
        hito.setModulo(modulo);
        hito.setTipo(tipo);
        hito.setNota(promedio);
        hito.setAprobado(promedio >= 4.0);
        hito.setFecha(LocalDate.now());
        hito.setObservacion(promedio >= 4.0 ?
                String.format("Módulo aprobado con promedio %.1f", promedio) :
                String.format("Módulo reprobado con promedio %.1f", promedio));
        hitoService.registrar(hito);
        return "redirect:/admin/hitos/" + matriculaId;
    }

    // Validar que el módulo no esté cerrado para EVALUACION
    if (tipo == Hito.Tipo.EVALUACION &&
            hitoService.moduloYaCompletado(matriculaId, moduloId)) {
        cargarModeloHitos(matriculaId, model);
        model.addAttribute("error",
                "No se pueden agregar evaluaciones a un módulo ya cerrado.");
        return "admin/hitos";
    }

    // Guardar hito normal (EVALUACION o ASISTENCIA)
    Matricula matricula = matriculaService.findById(matriculaId)
            .orElseThrow(() -> new RuntimeException("Matrícula no encontrada"));
    Modulo modulo = moduloRepository.findById(moduloId)
            .orElseThrow(() -> new RuntimeException("Módulo no encontrado"));
    Hito hito = new Hito();
    hito.setMatricula(matricula);
    hito.setModulo(modulo);
    hito.setTipo(tipo);
    hito.setNota(nota);
    hito.setFecha(LocalDate.now());
    hito.setObservacion(observacion);
    hitoService.registrar(hito);
    return "redirect:/admin/hitos/" + matriculaId;
}

    // ─── MÉTODO PRIVADO ───────────────────────────────────────
    private void cargarModeloHitos(Long matriculaId, Model model) {
        matriculaService.findById(matriculaId).ifPresent(m -> {
            model.addAttribute("hitos",
                    hitoService.findByMatriculaId(matriculaId));
            model.addAttribute("matriculaId", matriculaId);
            model.addAttribute("modulos", moduloRepository
                    .findByProgramaIdOrderByOrden(m.getPrograma().getId()));
            model.addAttribute("nombreEstudiante",
                    m.getUsuario().getNombre() + " " + m.getUsuario().getApellido());
            model.addAttribute("nombrePrograma",
                    m.getPrograma().getNombre());
        });
    }
}