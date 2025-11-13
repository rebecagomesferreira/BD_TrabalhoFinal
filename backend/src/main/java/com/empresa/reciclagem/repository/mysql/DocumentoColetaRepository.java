package com.empresa.reciclagem.repository.mysql;

import com.empresa.reciclagem.model.mysql.DocumentoColeta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DocumentoColetaRepository extends JpaRepository<DocumentoColeta, String> {

    @Query("SELECT d FROM DocumentoColeta d WHERE d.data = :data")
    List<DocumentoColeta> findByData(@Param("data") LocalDate data);

    @Query("SELECT d FROM DocumentoColeta d WHERE d.funcionario.idFuncionario = :idFuncionario")
    List<DocumentoColeta> findByFuncionarioIdFuncionario(@Param("idFuncionario") String idFuncionario);
}