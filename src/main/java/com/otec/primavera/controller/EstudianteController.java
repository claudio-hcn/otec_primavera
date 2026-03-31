package com.otec.primavera.controller;

import com.otec.primavera.dto.ProgresoDTO;
import com.otec.primavera.model.Matricula;
import com.otec.primavera.model.Usuario;
import com.otec.primavera.service.HitoService;
import com.otec.primavera.service.MatriculaService;
import com.otec.primavera.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/estudiante")
public class EstudianteController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private MatriculaService matriculaService;

    @Autowired
    private HitoService hitoService;

    @GetMapping("/portal")
    public String portal(Authentication authentication, Model model) {
        String email = authentication.getName();
        Usuario estudiante = usuarioService.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        model.addAttribute("estudiante", estudiante);

        matriculaService.findByUsuarioId(estudiante.getId())
                .ifPresent(m -> model.addAttribute("matricula", m));

        return "estudiante/portal";
    }

    @GetMapping("/progreso")
    public String progreso(Authentication authentication, Model model) {
        String email = authentication.getName();
        Usuario estudiante = usuarioService.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Matricula matricula = matriculaService.findByUsuarioId(estudiante.getId())
                .orElseThrow(() -> new RuntimeException("No tienes una matrícula activa"));

        ProgresoDTO progreso = hitoService.calcularProgreso(matricula);
        model.addAttribute("progreso", progreso);

        return "estudiante/progreso";
    }
}