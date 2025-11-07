package com.empresa.reciclagem.model.mysql;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "CLIENTE")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {
    
    @Id
    @Column(name = "id_cliente", length = 15)
    private String idCliente;
    
    @Column(name = "nome", length = 255, nullable = false)
    private String nome;
    
    @Column(name = "cpf_cnpj", length = 20, nullable = false, unique = true)
    private String cpfCnpj;
    
    @Column(name = "telefone", length = 20)
    private String telefone;
    
    @Column(name = "email", length = 255)
    private String email;
}
