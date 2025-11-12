package com.empresa.reciclagem.repository.mysql;

import com.empresa.reciclagem.model.mysql.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    // Busca o usuário pelo nome de login
    Optional<Usuario> findByNomeUsuario(String nomeUsuario);
}
