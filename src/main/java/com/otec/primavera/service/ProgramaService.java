package com.otec.primavera.service;

import com.otec.primavera.dto.ProgramaDTO;
import com.otec.primavera.model.Programa;
import com.otec.primavera.repository.ProgramaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProgramaService {

    @Autowired
    private ProgramaRepository programaRepository;

    public Programa guardar(Programa programa) {
        if (programaRepository.existsByNombre(programa.getNombre())) {
            throw new RuntimeException("Ya existe un programa con ese nombre");
        }
        return programaRepository.save(programa);
    }

    public Programa actualizar(Programa programa) {
        return programaRepository.save(programa);
    }

    public void eliminar(Long id) {
        programaRepository.deleteById(id);
    }

    public Optional<Programa> findById(Long id) {
        return programaRepository.findById(id);
    }

    public Iterable<Programa> findAll() {
        return programaRepository.findAll();
    }

    public List<Programa> findByEstado(Programa.Estado estado) {
        return programaRepository.findByEstado(estado);
    }

    public List<ProgramaDTO> findAllDTO() {
        List<ProgramaDTO> lista = new ArrayList<>();
        for (Programa p : programaRepository.findAll()) {
            lista.add(new ProgramaDTO(
                p.getId(),
                p.getNombre(),
                p.getDescripcion(),
                p.getDuracionHoras(),
                p.getTecnologia(),
                p.getModalidad(),
                p.getNivel(),
                p.getEstado()
            ));
        }
        return lista;
    }
}