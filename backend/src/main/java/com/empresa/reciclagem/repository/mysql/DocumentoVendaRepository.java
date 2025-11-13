package com.empresa.reciclagem.repository.mysql;

import com.empresa.reciclagem.model.mysql.DocumentoVenda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DocumentoVendaRepository extends JpaRepository<DocumentoVenda, String> {

    @Query("SELECT d FROM DocumentoVenda d WHERE d.data = :data")
    List<DocumentoVenda> findByData(@Param("data") LocalDate data);

    @Query("SELECT d FROM DocumentoVenda d WHERE d.cliente.idCliente = :idCliente")
    List<DocumentoVenda> findByClienteIdCliente(@Param("idCliente") String idCliente);
}