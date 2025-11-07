package com.empresa.reciclagem.repository.mongodb;

import com.empresa.reciclagem.model.mongodb.MaterialDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MaterialMongoRepository extends MongoRepository<MaterialDocument, String> {
    List<MaterialDocument> findByTipo(String tipo);
}
