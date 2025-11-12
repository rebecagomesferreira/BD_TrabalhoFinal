package com.empresa.reciclagem.service;

import com.empresa.reciclagem.dto.LoginRequest;
import com.empresa.reciclagem.dto.LoginResponse;
import com.empresa.reciclagem.model.mongodb.LogAcesso;
import com.empresa.reciclagem.model.mysql.GrupoUsuario;
import com.empresa.reciclagem.model.mysql.Usuario;
import com.empresa.reciclagem.repository.mongodb.LogAcessoRepository;
import com.empresa.reciclagem.repository.mysql.GrupoUsuarioRepository;
import com.empresa.reciclagem.repository.mysql.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private GrupoUsuarioRepository grupoUsuarioRepository;

    @Autowired
    private LogAcessoRepository logRepository;

    // --- LOGIN ---
    public LoginResponse autenticar(LoginRequest request) {
        var usuarioOpt = usuarioRepository.findByNomeUsuario(request.getNomeUsuario());

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
        return usuarioRepository.findByNomeUsuario(nomeUsuario).isPresent();
    }

    public GrupoUsuario buscarGrupo(String idGrupo) {
        return grupoUsuarioRepository.findById(idGrupo)
                .orElseThrow(() -> new RuntimeException("Grupo inválido!"));
    }

    public void salvarUsuario(Usuario usuario) {
        usuarioRepository.save(usuario);
    }
}
