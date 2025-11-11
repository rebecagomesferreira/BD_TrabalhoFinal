package com.empresa.reciclagem.model.mongodb;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;
import java.util.List;

@Document(collection = "Coletas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ColetaDocument {
    
    @Id
    private String id;
    
    @Field("data_coleta")
    private LocalDate dataColeta;
    
    @Field("local_coleta")
    private String localColeta;
    
    @Field("coletor")
    private Coletor coletor;
    
    @Field("fornecedor")
    private FornecedorInfo fornecedor;
    
    @Field("materiais_coletados")
    private List<MaterialColetado> materiaisColetados;
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Coletor {
        @Field("id_funcionario")
        private String idFuncionario;
        
        @Field("nome")
        private String nome;
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FornecedorInfo {
        @Field("id_fornecedor")
        private String idFornecedor;
        
        @Field("nome")
        private String nome;
        
        @Field("cpf_cnpj")
        private String cpfCnpj;
        
        @Field("tipo")
        private String tipo;
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MaterialColetado {
        @Field("tipo_material")
        private String tipoMaterial;
        
        @Field("quantidade_kg")
        private Integer quantidadeKg;
        
        @Field("recebido_por_funcionario")
        private String recebidoPorFuncionario;
    }
}
