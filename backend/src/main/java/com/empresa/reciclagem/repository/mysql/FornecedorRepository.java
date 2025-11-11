package com.empresa.reciclagem.repository.mysql;

import com.empresa.reciclagem.model.mysql.Fornecedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FornecedorRepository extends JpaRepository<Fornecedor, String> {
    List<Fornecedor> findByTipoFornecedor(String tipoFornecedor);
    Optional<Fornecedor> findByCpfCnpj(String cpfCnpj);
}
