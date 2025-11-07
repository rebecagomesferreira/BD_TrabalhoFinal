package com.empresa.reciclagem.model.mongodb;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Document(collection = "Funcionarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FuncionarioDocument {
    
    @Id
    private String id;
    
    @Field("nome")
    private String nome;
    
    @Field("cargo")
    private String cargo;
    
    @Field("cpf_cnpj")
    private String cpfCnpj;
    
    @Field("salario")
    private BigDecimal salario;
    
    @Field("endereco")
    private String endereco;
    
    @Field("data_nascimento")
    private LocalDate dataNascimento;
    
    @Field("contato")
    private Contato contato;
    
    @Field("seguranca")
    private Seguranca seguranca;
    
    @Field("responsabilidades")
    private Responsabilidades responsabilidades;
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Contato {
        @Field("telefone")
        private String telefone;
        
        @Field("email")
        private String email;
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Seguranca {
        @Field("nome_grupo")
        private String nomeGrupo;
        
        @Field("nivel_acesso")
        private Integer nivelAcesso;
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Responsabilidades {
        @Field("atende_cliente_ids")
        private List<String> atendeClienteIds;
        
        @Field("recebe_material_tipos")
        private List<String> recebeMaterialTipos;
        
        @Field("separa_material_tipos")
        private List<String> separaMaterialTipos;
    }
}
