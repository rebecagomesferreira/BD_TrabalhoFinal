package com.empresa.reciclagem.repository.mysql;

import com.empresa.reciclagem.model.mysql.Estoque;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EstoqueRepository extends JpaRepository<Estoque, Integer> {

    @Query("SELECT e FROM Estoque e WHERE e.nivelAtual < e.nivelMinimo")
    List<Estoque> findEstoqueBaixo();
}