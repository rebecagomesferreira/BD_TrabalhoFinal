package com.empresa.reciclagem.model.mysql;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ESTOQUE")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Estoque {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_estoque")
    private Integer idEstoque;
    
    @Column(name = "localizacao", length = 255, nullable = false)
    private String localizacao;
    
    @Column(name = "capacidade", nullable = false)
    private Integer capacidade;
    
    @Column(name = "nivel_atual")
    private Integer nivelAtual;
    
    @Column(name = "nivel_minimo")
    private Integer nivelMinimo;
}
