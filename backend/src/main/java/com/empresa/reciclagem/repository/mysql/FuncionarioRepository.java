package com.empresa.reciclagem.repository.mysql;

import com.empresa.reciclagem.model.mysql.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, Integer> {

    @Query("SELECT f FROM Funcionario f WHERE f.cargo = :cargo")
    List<Funcionario> findByCargo(@Param("cargo") String cargo);

    @Query("SELECT f FROM Funcionario f WHERE f.cpfCnpj = :cpfCnpj")
    Optional<Funcionario> findByCpfCnpj(@Param("cpfCnpj") String cpfCnpj);
}