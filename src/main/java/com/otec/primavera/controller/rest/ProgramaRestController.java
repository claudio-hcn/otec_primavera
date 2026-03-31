package com.otec.primavera.controller.rest;

import com.otec.primavera.dto.ProgramaDTO;
import com.otec.primavera.model.Programa;
import com.otec.primavera.service.ProgramaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/programas")
public class ProgramaRestController {

    @Autowired
    private ProgramaService programaService;

    @GetMapping
    public ResponseEntity<List<ProgramaDTO>> listarProgramas() {
        return ResponseEntity.ok(programaService.findAllDTO());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProgramaDTO> getPrograma(@PathVariable Long id) {
        return programaService.findById(id)
                .map(p -> ResponseEntity.ok(new ProgramaDTO(
                        p.getId(),
                        p.getNombre(),
                        p.getDescripcion(),
                        p.getDuracionHoras(),
                        p.getTecnologia(),
                        p.getModalidad(),
                        p.getNivel(),
                        p.getEstado()
                )))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Programa> crearPrograma(@RequestBody Programa programa) {
        try {
            return ResponseEntity.ok(programaService.guardar(programa));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPrograma(@PathVariable Long id) {
        programaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}