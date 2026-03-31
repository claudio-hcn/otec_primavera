package com.otec.primavera.repository;

import com.otec.primavera.model.Matricula;
import org.springframework.data.repository.CrudRepository;
import java.util.List;
import java.util.Optional;

public interface MatriculaRepository extends CrudRepository<Matricula, Long> {

    Optional<Matricula> findByUsuarioId(Long usuarioId);

    boolean existsByUsuarioIdAndEstado(Long usuarioId, Matricula.Estado estado);

    List<Matricula> findByProgramaId(Long programaId);
}