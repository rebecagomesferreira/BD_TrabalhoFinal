package com.empresa.reciclagem.service;

import com.empresa.reciclagem.model.mongodb.VendaDocument;
import com.empresa.reciclagem.repository.mongodb.VendaMongoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VendaService {
    
    private final VendaMongoRepository vendaRepository;
    
    public List<VendaDocument> findAll() {
        return vendaRepository.findAll();
    }
    
    public VendaDocument findById(String id) {
        return vendaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Venda não encontrada: " + id));
    }
    
    public List<VendaDocument> findByDataVenda(LocalDate dataVenda) {
        return vendaRepository.findByDataVenda(dataVenda);
    }
    
    public List<VendaDocument> findByClienteId(String idCliente) {
        return vendaRepository.findByClienteIdCliente(idCliente);
    }
    
    public VendaDocument save(VendaDocument venda) {
        return vendaRepository.save(venda);
    }
    
    public void deleteById(String id) {
        vendaRepository.deleteById(id);
    }
}
