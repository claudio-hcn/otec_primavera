package com.otec.primavera.repository;

import com.otec.primavera.model.Hito;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface HitoRepository extends CrudRepository<Hito, Long> {

    List<Hito> findByMatriculaId(Long matriculaId);

    List<Hito> findByMatriculaIdAndTipo(Long matriculaId, Hito.Tipo tipo);

    int countByMatriculaIdAndTipo(Long matriculaId, Hito.Tipo tipo);

    boolean existsByMatriculaIdAndModuloIdAndTipo(Long matriculaId, Long moduloId, Hito.Tipo tipo);

    List<Hito> findByMatriculaIdAndModuloIdAndTipo(Long matriculaId, Long moduloId, Hito.Tipo tipo);
}