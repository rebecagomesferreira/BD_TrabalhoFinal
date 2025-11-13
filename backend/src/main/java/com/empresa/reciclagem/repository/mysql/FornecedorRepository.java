package com.empresa.reciclagem.repository.mysql;

import com.empresa.reciclagem.model.mysql.Fornecedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FornecedorRepository extends JpaRepository<Fornecedor, Integer> {
    // Métodos customizados podem ser adicionados aqui
}