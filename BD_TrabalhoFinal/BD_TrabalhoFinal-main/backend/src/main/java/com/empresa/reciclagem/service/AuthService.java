package com.empresa.reciclagem.service;

import com.empresa.reciclagem.dto.LoginRequest;
import com.empresa.reciclagem.dto.LoginResponse;
import com.empresa.reciclagem.model.mysql.Usuario;
import com.empresa.reciclagem.repository.mysql.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public LoginResponse autenticar(LoginRequest request) {
        var usuarioOpt = usuarioRepository.findByNomeUsuario(request.getNomeUsuario());

        if (usuarioOpt.isEmpty()) {
            return new LoginResponse(false, "Usuário não encontrado", null);
        }

        Usuario usuario = usuarioOpt.get();

        // Comparação simples de senha
        if (!usuario.getSenha().equals(request.getSenha())) {
            return new LoginResponse(false, "Senha incorreta", null);
        }

        if (!Boolean.TRUE.equals(usuario.getAtivo())) {
            return new LoginResponse(false, "Usuário inativo", null);
        }

        return new LoginResponse(true, "Login realizado com sucesso!", usuario.getGrupo().getNomeGrupo());
    }
}
