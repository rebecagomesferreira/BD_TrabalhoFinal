package com.empresa.reciclagem.repository.mongodb;

import com.empresa.reciclagem.model.mongodb.LogAcesso;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogAcessoRepository extends MongoRepository<LogAcesso, String> {
}

