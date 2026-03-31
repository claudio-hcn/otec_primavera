package com.otec.primavera.repository;

import com.otec.primavera.model.Modulo;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface ModuloRepository extends CrudRepository<Modulo, Long> {

    List<Modulo> findByProgramaIdOrderByOrden(Long programaId);

    int countByProgramaId(Long programaId);
}