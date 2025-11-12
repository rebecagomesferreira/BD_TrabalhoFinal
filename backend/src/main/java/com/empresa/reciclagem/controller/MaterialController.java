package com.empresa.reciclagem.controller;

import com.empresa.reciclagem.model.mysql.Material;
import com.empresa.reciclagem.service.MaterialService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/materiais")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class MaterialController {
    
    private final MaterialService materialService;
    
    @GetMapping
    public ResponseEntity<List<Material>> findAll() {
        return ResponseEntity.ok(materialService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Material> findById(@PathVariable Long id) {
        return ResponseEntity.ok(materialService.findById(id));
    }
    
    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<Material>> findByTipo(@PathVariable String tipo) {
        return ResponseEntity.ok(materialService.findByTipo(tipo));
    }
    
    @PostMapping
    public ResponseEntity<Material> create(@RequestBody Material material) {
        return ResponseEntity.status(HttpStatus.CREATED).body(materialService.save(material));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Material> update(@PathVariable Long id, @RequestBody Material materialAtualizado) {
        Material existente = materialService.findById(id);

        // Atualiza apenas os campos permitidos
        existente.setTipo(materialAtualizado.getTipo());
        existente.setDescricao(materialAtualizado.getDescricao());
        existente.setPrecoKg(materialAtualizado.getPrecoKg());

        Material salvo = materialService.save(existente);
        return ResponseEntity.ok(salvo);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        materialService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
