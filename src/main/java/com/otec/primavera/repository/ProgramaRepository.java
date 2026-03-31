package com.otec.primavera.repository;

import com.otec.primavera.model.Programa;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface ProgramaRepository extends CrudRepository<Programa, Long> {

    List<Programa> findByEstado(Programa.Estado estado);

    List<Programa> findByTecnologia(String tecnologia);

    boolean existsByNombre(String nombre);
}