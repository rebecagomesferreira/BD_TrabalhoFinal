package com.empresa.reciclagem.repository.mysql;

import com.empresa.reciclagem.model.mysql.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional; // ADICIONE ESTE IMPORT

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Optional<Usuario> findByNomeUsuario(String nomeUsuario);
}