package com.empresa.reciclagem.model.mysql;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "FORNECEDOR")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Fornecedor {
    
    @Id
    @Column(name = "id_fornecedor", length = 15)
    private String idFornecedor;
    
    @Column(name = "cpf_cnpj", length = 20, nullable = false, unique = true)
    private String cpfCnpj;
    
    @Column(name = "nome", length = 255, nullable = false)
    private String nome;
    
    @Column(name = "telefone", length = 20)
    private String telefone;
    
    @Column(name = "tipo_fornecedor", length = 50)
    private String tipoFornecedor;
}
