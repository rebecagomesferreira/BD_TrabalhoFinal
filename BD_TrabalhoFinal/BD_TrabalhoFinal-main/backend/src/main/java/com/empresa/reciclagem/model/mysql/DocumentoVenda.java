package com.empresa.reciclagem.model.mysql;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "DOCUMENTO_VENDA")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocumentoVenda {
    
    @Id
    @Column(name = "id_doc_venda", length = 15)
    private String idDocVenda;
    
    @Column(name = "data", nullable = false)
    private LocalDate data;
    
    @Column(name = "valor", precision = 10, scale = 2, nullable = false)
    private BigDecimal valor;
    
    @ManyToOne
    @JoinColumn(name = "id_cliente", nullable = false)
    private Cliente cliente;
    
    @ManyToOne
    @JoinColumn(name = "id_funcionario", nullable = false)
    private Funcionario funcionario;
    
    @ManyToOne
    @JoinColumn(name = "id_tipo_pagamento", nullable = false)
    private TipoPagamento tipoPagamento;
}
