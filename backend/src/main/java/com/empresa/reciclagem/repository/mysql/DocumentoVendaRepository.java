package com.empresa.reciclagem.repository.mysql;

import com.empresa.reciclagem.model.mysql.DocumentoVenda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DocumentoVendaRepository extends JpaRepository<DocumentoVenda, String> {
    List<DocumentoVenda> findByData(LocalDate data);
    List<DocumentoVenda> findByClienteIdCliente(String idCliente);
}
