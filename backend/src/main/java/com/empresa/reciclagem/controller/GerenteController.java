package com.empresa.reciclagem.controller;

import com.empresa.reciclagem.model.mysql.Material;
import com.empresa.reciclagem.model.mysql.Fornecedor;
import com.empresa.reciclagem.repository.mysql.MaterialRepository;
import com.empresa.reciclagem.repository.mysql.FornecedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/gerente")
@CrossOrigin(origins = "*")
public class GerenteController {

    @Autowired
    private MaterialRepository materialRepository;

    @Autowired
    private FornecedorRepository fornecedorRepository;

    // Listar materiais
    @GetMapping("/materiais")
    public ResponseEntity<List<Material>> listarMateriais() {
        List<Material> materiais = materialRepository.findAll();
        return ResponseEntity.ok(materiais);
    }

    // Listar fornecedores
    @GetMapping("/fornecedores")
    public ResponseEntity<List<Fornecedor>> listarFornecedores() {
        List<Fornecedor> fornecedores = fornecedorRepository.findAll();
        return ResponseEntity.ok(fornecedores);
    }

    // Relatório simples
    @GetMapping("/relatorio")
    public ResponseEntity<String> gerarRelatorio() {
        long totalMateriais = materialRepository.count();
        long totalFornecedores = fornecedorRepository.count();
        return ResponseEntity.ok("Relatório: " + totalMateriais + " materiais e " + totalFornecedores + " fornecedores cadastrados.");
    }
}
