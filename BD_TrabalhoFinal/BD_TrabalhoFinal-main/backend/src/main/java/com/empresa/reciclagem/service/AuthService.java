package com.empresa.reciclagem.service;

import com.empresa.reciclagem.dto.LoginRequest;
import com.empresa.reciclagem.dto.LoginResponse;
import com.empresa.reciclagem.model.mysql.Usuario;
import com.empresa.reciclagem.repository.mysql.UsuarioRepository;
import com.empresa.reciclagem.repository.mongodb.LogAcessoRepository;
import com.empresa.reciclagem.model.mongodb.LogAcesso;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private LogAcessoRepository logRepository;

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

        // Registrar log no MongoDB
        logRepository.save(new LogAcesso(
                usuario.getNomeUsuario(),
                "Login realizado com sucesso"
        ));

        return new LoginResponse(true, "Login realizado com sucesso!", usuario.getGrupo().getNomeGrupo());
    }
}
