package br.com.mrchagas.repository;

import br.com.mrchagas.entity.Usuario;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends CrudRepository<Usuario, Integer> {
    Optional<Usuario> findByUsername(String username);
}
