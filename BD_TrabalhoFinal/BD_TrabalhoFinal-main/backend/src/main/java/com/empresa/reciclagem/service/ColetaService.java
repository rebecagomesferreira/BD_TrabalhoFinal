package com.empresa.reciclagem.service;

import com.empresa.reciclagem.model.mongodb.ColetaDocument;
import com.empresa.reciclagem.repository.mongodb.ColetaMongoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ColetaService {
    
    private final ColetaMongoRepository coletaRepository;
    
    public List<ColetaDocument> findAll() {
        return coletaRepository.findAll();
    }
    
    public ColetaDocument findById(String id) {
        return coletaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Coleta não encontrada: " + id));
    }
    
    public List<ColetaDocument> findByDataColeta(LocalDate dataColeta) {
        return coletaRepository.findByDataColeta(dataColeta);
    }
    
    public ColetaDocument save(ColetaDocument coleta) {
        return coletaRepository.save(coleta);
    }
    
    public void deleteById(String id) {
        coletaRepository.deleteById(id);
    }
}
