package com.empresa.reciclagem.model.mysql;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "MATERIAL")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Material {
    
    @Id
    @Column(name = "id_material", length = 15)
    private String idMaterial;
    
    @Column(name = "tipo", length = 30, nullable = false)
    private String tipo;
    
    @Column(name = "descricao", length = 255)
    private String descricao;
    
    @Column(name = "preco_kg", precision = 10, scale = 2, nullable = false)
    private BigDecimal precoKg;
}
