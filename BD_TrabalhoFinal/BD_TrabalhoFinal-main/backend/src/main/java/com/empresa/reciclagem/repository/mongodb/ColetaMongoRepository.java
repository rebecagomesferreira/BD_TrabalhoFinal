package com.empresa.reciclagem.repository.mongodb;

import com.empresa.reciclagem.model.mongodb.ColetaDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ColetaMongoRepository extends MongoRepository<ColetaDocument, String> {
    List<ColetaDocument> findByDataColeta(LocalDate dataColeta);
    List<ColetaDocument> findByColetorIdFuncionario(String idFuncionario);
}
