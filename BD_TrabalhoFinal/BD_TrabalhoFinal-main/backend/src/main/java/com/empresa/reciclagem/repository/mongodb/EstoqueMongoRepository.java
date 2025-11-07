package com.empresa.reciclagem.repository.mongodb;

import com.empresa.reciclagem.model.mongodb.EstoqueDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EstoqueMongoRepository extends MongoRepository<EstoqueDocument, String> {
    @Query("{ 'nivel_atual': { $lt: '$nivel_minimo' } }")
    List<EstoqueDocument> findEstoqueBaixo();
}
