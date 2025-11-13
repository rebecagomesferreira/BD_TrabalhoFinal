package com.empresa.reciclagem.repository.mysql;

import com.empresa.reciclagem.model.mysql.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

    @Query("SELECT c FROM Cliente c WHERE c.cpfCnpj = :cpfCnpj")
    Optional<Cliente> findByCpfCnpj(@Param("cpfCnpj") String cpfCnpj);
}