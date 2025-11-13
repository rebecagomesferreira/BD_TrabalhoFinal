package com.empresa.reciclagem.service;

import com.empresa.reciclagem.dto.LoginRequest;
import com.empresa.reciclagem.dto.LoginResponse;
import com.empresa.reciclagem.model.mongodb.LogAcesso;
import com.empresa.reciclagem.model.mysql.GrupoUsuario;
import com.empresa.reciclagem.model.mysql.Usuario;
import com.empresa.reciclagem.repository.mongodb.LogAcessoRepository;
import com.empresa.reciclagem.repository.mysql.GrupoUsuarioRepository;
import com.empresa.reciclagem.repository.mysql.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioService usuarioService; // MUDEI: Use o Service ao invés do Repository
    private final GrupoUsuarioRepository grupoUsuarioRepository;
    private final LogAcessoRepository logRepository;

    // --- LOGIN ---
    public LoginResponse autenticar(LoginRequest request) {
        var usuarioOpt = usuarioService.findByNomeUsuario(request.getNomeUsuario()); // MUDEI: Use o Service

        if (usuarioOpt.isEmpty()) {
            return new LoginResponse(false, "Usuário não encontrado", null);
        }

        Usuario usuario = usuarioOpt.get();

        if (!usuario.getSenha().equals(request.getSenha())) {
            return new LoginResponse(false, "Senha incorreta", null);
        }

        if (!Boolean.TRUE.equals(usuario.getAtivo())) {
            return new LoginResponse(false, "Usuário inativo", null);
        }

        // Log de acesso
        logRepository.save(new LogAcesso(
                usuario.getNomeUsuario(),
                "Login realizado com sucesso"
        ));

        return new LoginResponse(true, "Login realizado com sucesso!", usuario.getGrupo().getNomeGrupo());
    }

    // --- REGISTRO ---
    public boolean usuarioExiste(String nomeUsuario) {
        return usuarioService.findByNomeUsuario(nomeUsuario).isPresent(); // MUDEI: Use o Service
    }

    public GrupoUsuario buscarGrupo(String idGrupo) {
        return grupoUsuarioRepository.findById(idGrupo)
                .orElseThrow(() -> new RuntimeException("Grupo não encontrado: " + idGrupo));
    }

    public void salvarUsuario(Usuario usuario) {
        usuarioService.salvar(usuario); // MUDEI: Use o Service
    }
}