package com.empresa.reciclagem.model.mysql;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TIPO_PAGAMENTO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TipoPagamento {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tipo_pagamento")
    private Integer idTipoPagamento;
    
    @Column(name = "descricao", length = 100, nullable = false)
    private String descricao;
    
    @Column(name = "parcelamento")
    private Boolean parcelamento = false;
    
    @Column(name = "num_parcelas")
    private Integer numParcelas;
}
