package com.empresa.reciclagem.repository.mysql;

import com.empresa.reciclagem.model.mysql.GrupoUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GrupoUsuarioRepository extends JpaRepository<GrupoUsuario, String> {
    // Já está correto!
}