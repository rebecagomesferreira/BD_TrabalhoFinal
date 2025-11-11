package com.empresa.reciclagem.service;

import com.empresa.reciclagem.model.mysql.Material;
import com.empresa.reciclagem.repository.mysql.MaterialRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MaterialService {
    
    private final MaterialRepository materialRepository;
    
    public List<Material> findAll() {
        return materialRepository.findAll();
    }
    
    public Material findById(String id) {
        return materialRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Material não encontrado: " + id));
    }
    
    public List<Material> findByTipo(String tipo) {
        return materialRepository.findByTipo(tipo);
    }
    
    @Transactional
    public Material save(Material material) {
        return materialRepository.save(material);
    }
    
    @Transactional
    public void deleteById(String id) {
        materialRepository.deleteById(id);
    }
}
