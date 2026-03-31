package com.otec.primavera.service;

import com.otec.primavera.model.Matricula;
import com.otec.primavera.model.Programa;
import com.otec.primavera.model.Usuario;
import com.otec.primavera.repository.MatriculaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.Optional;

@Service
public class MatriculaService {

    @Autowired
    private MatriculaRepository matriculaRepository;

    public Matricula matricular(Usuario estudiante, Programa programa) {
        if (matriculaRepository.existsByUsuarioIdAndEstado(
                estudiante.getId(), Matricula.Estado.EN_CURSO)) {
            throw new RuntimeException("El estudiante ya tiene un programa en curso");
        }
        Matricula matricula = new Matricula();
        matricula.setUsuario(estudiante);
        matricula.setPrograma(programa);
        matricula.setFechaInicio(LocalDate.now());
        matricula.setEstado(Matricula.Estado.EN_CURSO);
        return matriculaRepository.save(matricula);
    }

    public Matricula actualizarEstado(Long matriculaId, Matricula.Estado nuevoEstado) {
        Matricula matricula = matriculaRepository.findById(matriculaId)
                .orElseThrow(() -> new RuntimeException("Matrícula no encontrada"));
        matricula.setEstado(nuevoEstado);
        return matriculaRepository.save(matricula);
    }

    public Optional<Matricula> findByUsuarioId(Long usuarioId) {
        return matriculaRepository.findByUsuarioId(usuarioId);
    }
public Optional<Matricula> findById(Long id) {
    return matriculaRepository.findById(id);
}
    public Iterable<Matricula> findAll() {
        return matriculaRepository.findAll();
    }
}