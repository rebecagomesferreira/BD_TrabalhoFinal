package com.empresa.reciclagem.service;

import com.empresa.reciclagem.model.mysql.Funcionario;
import com.empresa.reciclagem.repository.mysql.FuncionarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FuncionarioService {
    
    private final FuncionarioRepository funcionarioRepository;
    
    public List<Funcionario> findAll() {
        return funcionarioRepository.findAll();
    }
    
    public Funcionario findById(String id) {
        return funcionarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Funcionário não encontrado: " + id));
    }
    
    public List<Funcionario> findByCargo(String cargo) {
        return funcionarioRepository.findByCargo(cargo);
    }
    
    @Transactional
    public Funcionario save(Funcionario funcionario) {
        return funcionarioRepository.save(funcionario);
    }
    
    @Transactional
    public void deleteById(String id) {
        funcionarioRepository.deleteById(id);
    }
}
