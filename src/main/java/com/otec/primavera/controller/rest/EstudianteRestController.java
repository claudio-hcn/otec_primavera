package com.otec.primavera.controller.rest;

import com.otec.primavera.dto.ProgresoDTO;
import com.otec.primavera.dto.UsuarioRegistroDTO;
import com.otec.primavera.dto.UsuarioResponseDTO;
import com.otec.primavera.model.Matricula;
import com.otec.primavera.model.Usuario;
import com.otec.primavera.service.HitoService;
import com.otec.primavera.service.MatriculaService;
import com.otec.primavera.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/estudiantes")
public class EstudianteRestController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private MatriculaService matriculaService;

    @Autowired
    private HitoService hitoService;

    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> listarEstudiantes() {
        return ResponseEntity.ok(usuarioService.findAllEstudiantes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> getEstudiante(@PathVariable Long id) {
        return usuarioService.findById(id)
                .map(u -> ResponseEntity.ok(new UsuarioResponseDTO(
                        u.getId(),
                        u.getNombre(),
                        u.getApellido(),
                        u.getEmail(),
                        u.getRol())))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/progreso")
    public ResponseEntity<ProgresoDTO> getProgreso(@PathVariable Long id) {
        return matriculaService.findByUsuarioId(id)
                .map(m -> ResponseEntity.ok(hitoService.calcularProgreso(m)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> crearEstudiante(
            @RequestBody UsuarioRegistroDTO dto) {
        try {
            dto.setRol(Usuario.Rol.ESTUDIANTE);
            Usuario usuario = usuarioService.registrar(dto);
            UsuarioResponseDTO response = new UsuarioResponseDTO(
                    usuario.getId(),
                    usuario.getNombre(),
                    usuario.getApellido(),
                    usuario.getEmail(),
                    usuario.getRol());
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

}