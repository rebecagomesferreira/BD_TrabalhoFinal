package com.empresa.reciclagem.repository.mysql;

import com.empresa.reciclagem.model.mysql.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, String> {
    List<Funcionario> findByCargo(String cargo);
    Optional<Funcionario> findByCpfCnpj(String cpfCnpj);
}
