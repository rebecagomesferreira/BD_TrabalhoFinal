package com.empresa.reciclagem.repository.mysql;

import com.empresa.reciclagem.model.mysql.Material;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MaterialRepository extends JpaRepository<Material, String> {
    List<Material> findByTipo(String tipo);
}
