package com.empresa.reciclagem.service;

import com.empresa.reciclagem.model.mysql.Usuario;
import com.empresa.reciclagem.repository.mysql.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    // --- MÉTODOS PARA O USUARIOCONTROLLER (Integer) ---
    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> buscarPorId(Integer id) {
        return usuarioRepository.findById(id);
    }

    public Usuario salvar(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public void deletar(Integer id) {
        usuarioRepository.deleteById(id);
    }

    // --- MÉTODOS PARA O ADMINCONTROLLER (List) ---
    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    // --- MÉTODOS PARA O AUTHSERVICE ---
    public Optional<Usuario> findByNomeUsuario(String nomeUsuario) {
        return usuarioRepository.findByNomeUsuario(nomeUsuario);
    }

    // --- MÉTODOS EXTRA PARA COMPATIBILIDADE ---
    public Usuario findById(Long id) {
        return usuarioRepository.findById(id.intValue())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado: " + id));
    }

    public Usuario save(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public void deleteById(Long id) {
        usuarioRepository.deleteById(id.intValue());
    }
}