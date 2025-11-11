package com.empresa.reciclagem.model.mongodb;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Document(collection = "Estoque")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstoqueDocument {
    
    @Id
    private String id;
    
    @Field("localizacao")
    private String localizacao;
    
    @Field("capacidade_total")
    private Integer capacidadeTotal;
    
    @Field("nivel_atual")
    private Integer nivelAtual;
    
    @Field("nivel_minimo")
    private Integer nivelMinimo;
    
    @Field("materiais_armazenados")
    private List<MaterialArmazenado> materiaisArmazenados;
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MaterialArmazenado {
        @Field("tipo_material")
        private String tipoMaterial;
        
        @Field("quantidade_kg")
        private Integer quantidadeKg;
        
        @Field("data_entrada")
        private String dataEntrada;
    }
}
