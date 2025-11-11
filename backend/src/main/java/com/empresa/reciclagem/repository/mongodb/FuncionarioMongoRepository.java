package com.empresa.reciclagem.repository.mongodb;

import com.empresa.reciclagem.model.mongodb.FuncionarioDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FuncionarioMongoRepository extends MongoRepository<FuncionarioDocument, String> {
    List<FuncionarioDocument> findByCargo(String cargo);
    List<FuncionarioDocument> findBySegurancaNomeGrupo(String nomeGrupo);
}
