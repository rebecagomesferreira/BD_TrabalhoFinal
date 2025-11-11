package com.empresa.reciclagem.repository.mysql;

import com.empresa.reciclagem.model.mysql.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, String> {
    Optional<Cliente> findByCpfCnpj(String cpfCnpj);
}
