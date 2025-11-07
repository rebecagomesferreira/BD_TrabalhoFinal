package com.empresa.reciclagem.repository.mysql;

import com.empresa.reciclagem.model.mysql.DocumentoColeta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DocumentoColetaRepository extends JpaRepository<DocumentoColeta, String> {
    List<DocumentoColeta> findByData(LocalDate data);
    List<DocumentoColeta> findByFuncionarioIdFuncionario(String idFuncionario);
}
