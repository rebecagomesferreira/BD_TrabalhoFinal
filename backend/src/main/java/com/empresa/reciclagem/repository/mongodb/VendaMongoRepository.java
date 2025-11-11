package com.empresa.reciclagem.repository.mongodb;

import com.empresa.reciclagem.model.mongodb.VendaDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface VendaMongoRepository extends MongoRepository<VendaDocument, String> {
    List<VendaDocument> findByDataVenda(LocalDate dataVenda);
    List<VendaDocument> findByClienteIdCliente(String idCliente);
}
