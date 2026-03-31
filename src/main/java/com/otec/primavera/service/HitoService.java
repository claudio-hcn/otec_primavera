package com.otec.primavera.service;

import com.otec.primavera.dto.ProgresoDTO;
import com.otec.primavera.model.Hito;
import com.otec.primavera.model.Matricula;
import com.otec.primavera.model.Modulo;
import com.otec.primavera.repository.HitoRepository;
import com.otec.primavera.repository.ModuloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class HitoService {

    @Autowired
    private HitoRepository hitoRepository;

    @Autowired
    private ModuloRepository moduloRepository;

    public Hito registrar(Hito hito) {
        return hitoRepository.save(hito);
    }

    public List<Hito> findByMatriculaId(Long matriculaId) {
        return hitoRepository.findByMatriculaId(matriculaId);
    }

    public ProgresoDTO calcularProgreso(Matricula matricula) {
        int totalModulos = moduloRepository.countByProgramaId(
                matricula.getPrograma().getId());
        int modulosCompletados = hitoRepository.countByMatriculaIdAndTipo(
                matricula.getId(), Hito.Tipo.MODULO_COMPLETADO);

        int porcentaje = totalModulos > 0
                ? (modulosCompletados * 100) / totalModulos
                : 0;

        List<ProgresoDTO.HitoDTO> hitosDTO = new ArrayList<>();
        for (Hito h : hitoRepository.findByMatriculaId(matricula.getId())) {
            hitosDTO.add(new ProgresoDTO.HitoDTO(
                h.getModulo().getNombre(),
                h.getTipo(),
                h.getNota(),
                h.getFecha(),
                h.getObservacion()
            ));
        }

        return new ProgresoDTO(
            matricula.getPrograma().getNombre(),
            matricula.getPrograma().getTecnologia(),
            matricula.getPrograma().getDuracionHoras(),
            matricula.getFechaInicio(),
            matricula.getEstado().toString(),
            porcentaje,
            hitosDTO
        );
    }

    public boolean moduloYaCompletado(Long matriculaId, Long moduloId) {
    return hitoRepository.existsByMatriculaIdAndModuloIdAndTipo(
            matriculaId, moduloId, Hito.Tipo.MODULO_COMPLETADO);
}

public double calcularPromedioModulo(Long matriculaId, Long moduloId) {
    List<Hito> evaluaciones = hitoRepository
            .findByMatriculaIdAndModuloIdAndTipo(
                    matriculaId, moduloId, Hito.Tipo.EVALUACION);
    if (evaluaciones.isEmpty()) return 0.0;
    return evaluaciones.stream()
            .filter(h -> h.getNota() != null)
            .mapToDouble(Hito::getNota)
            .average()
            .orElse(0.0);
}
}
