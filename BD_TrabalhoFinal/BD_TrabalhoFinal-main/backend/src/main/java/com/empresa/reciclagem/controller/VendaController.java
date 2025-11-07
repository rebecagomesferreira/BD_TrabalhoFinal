package com.empresa.reciclagem.controller;

import com.empresa.reciclagem.model.mongodb.VendaDocument;
import com.empresa.reciclagem.service.VendaService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/vendas")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class VendaController {
    
    private final VendaService vendaService;
    
    @GetMapping
    public ResponseEntity<List<VendaDocument>> findAll() {
        return ResponseEntity.ok(vendaService.findAll());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<VendaDocument> findById(@PathVariable String id) {
        return ResponseEntity.ok(vendaService.findById(id));
    }
    
    @GetMapping("/data/{data}")
    public ResponseEntity<List<VendaDocument>> findByData(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data) {
        return ResponseEntity.ok(vendaService.findByDataVenda(data));
    }
    
    @GetMapping("/cliente/{idCliente}")
    public ResponseEntity<List<VendaDocument>> findByCliente(@PathVariable String idCliente) {
        return ResponseEntity.ok(vendaService.findByClienteId(idCliente));
    }
    
    @PostMapping
    public ResponseEntity<VendaDocument> create(@RequestBody VendaDocument venda) {
        return ResponseEntity.status(HttpStatus.CREATED).body(vendaService.save(venda));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<VendaDocument> update(@PathVariable String id, @RequestBody VendaDocument venda) {
        venda.setId(id);
        return ResponseEntity.ok(vendaService.save(venda));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        vendaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
