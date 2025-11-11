package com.empresa.reciclagem.model.mysql;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "DOCUMENTO_COLETA")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocumentoColeta {
    
    @Id
    @Column(name = "id_doc_coleta", length = 15)
    private String idDocColeta;
    
    @Column(name = "data", nullable = false)
    private LocalDate data;
    
    @Column(name = "local", length = 255, nullable = false)
    private String local;
    
    @ManyToOne
    @JoinColumn(name = "id_funcionario", nullable = false)
    private Funcionario funcionario;
    
    @ManyToOne
    @JoinColumn(name = "id_fornecedor", nullable = false)
    private Fornecedor fornecedor;
}
