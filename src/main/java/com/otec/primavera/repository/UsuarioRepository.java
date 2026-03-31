package com.otec.primavera.repository;

import com.otec.primavera.model.Usuario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import java.util.Optional;

public interface UsuarioRepository extends CrudRepository<Usuario, Long> {

    Optional<Usuario> findByEmail(String email);

    boolean existsByEmail(String email);

    @Query("SELECT u FROM Usuario u WHERE u.rol = 'ESTUDIANTE'")
    Iterable<Usuario> findAllEstudiantes();
}
