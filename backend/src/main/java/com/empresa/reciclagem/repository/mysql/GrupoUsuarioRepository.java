package com.empresa.reciclagem.repository.mysql;

import com.empresa.reciclagem.model.mysql.GrupoUsuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GrupoUsuarioRepository extends JpaRepository<GrupoUsuario, String> {
}
