package com.empresa.reciclagem.controller;

import com.empresa.reciclagem.model.mongodb.ColetaDocument;
import com.empresa.reciclagem.service.ColetaService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/coletas")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ColetaController {
    
    private final ColetaService coletaService;
    
    @GetMapping
    public ResponseEntity<List<ColetaDocument>> findAll() {
        return ResponseEntity.ok(coletaService.findAll());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ColetaDocument> findById(@PathVariable String id) {
        return ResponseEntity.ok(coletaService.findById(id));
    }
    
    @GetMapping("/data/{data}")
    public ResponseEntity<List<ColetaDocument>> findByData(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data) {
        return ResponseEntity.ok(coletaService.findByDataColeta(data));
    }
    
    @PostMapping
    public ResponseEntity<ColetaDocument> create(@RequestBody ColetaDocument coleta) {
        return ResponseEntity.status(HttpStatus.CREATED).body(coletaService.save(coleta));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ColetaDocument> update(@PathVariable String id, @RequestBody ColetaDocument coleta) {
        coleta.setId(id);
        return ResponseEntity.ok(coletaService.save(coleta));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        coletaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
