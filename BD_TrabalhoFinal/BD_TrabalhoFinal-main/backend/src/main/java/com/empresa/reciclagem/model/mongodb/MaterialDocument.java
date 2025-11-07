package com.empresa.reciclagem.model.mongodb;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigDecimal;
import java.util.List;

@Document(collection = "Materiais")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MaterialDocument {
    
    @Id
    private String id;
    
    @Field("tipo")
    private String tipo;
    
    @Field("descricao")
    private String descricao;
    
    @Field("preco_kg")
    private BigDecimal precoKg;
    
    @Field("fornecedores")
    private List<FornecedorMaterial> fornecedores;
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FornecedorMaterial {
        @Field("id_fornecedor")
        private String idFornecedor;
        
        @Field("nome_fornecedor")
        private String nomeFornecedor;
    }
}
