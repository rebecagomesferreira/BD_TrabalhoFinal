package com.empresa.reciclagem.controller;

import com.empresa.reciclagem.dto.LoginRequest;
import com.empresa.reciclagem.dto.LoginResponse;
import com.empresa.reciclagem.model.mysql.GrupoUsuario;
import com.empresa.reciclagem.model.mysql.Usuario;
import com.empresa.reciclagem.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map; // ← ADICIONE ESTE IMPORT

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private AuthService authService;

    // --- LOGIN ---
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        LoginResponse response = authService.autenticar(request);
        if (!response.isAutenticado()) {
            return ResponseEntity.status(401).body(response);
        }
        return ResponseEntity.ok(response);
    }

    // --- REGISTRAR NOVO USUÁRIO ---
    @PostMapping("/registrar")
    public ResponseEntity<?> registrar(@RequestBody Map<String, Object> payload) {
        try {
            String nomeUsuario = (String) payload.get("nomeUsuario");
            String senha = (String) payload.get("senha");
            String idGrupo = (String) payload.get("idGrupo");

            // Verifica se já existe usuário com mesmo nome
            if (authService.usuarioExiste(nomeUsuario)) {
                return ResponseEntity.badRequest().body("Usuário já existe!");
            }

            // Valida o grupo
            if (idGrupo == null || idGrupo.trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Grupo de usuário é obrigatório!");
            }

            GrupoUsuario grupo = authService.buscarGrupo(idGrupo);
            if (grupo == null) {
                return ResponseEntity.badRequest().body("Grupo inválido!");
            }

            // Cria o usuário
            Usuario usuario = new Usuario();
            usuario.setNomeUsuario(nomeUsuario);
            usuario.setSenha(senha);
            usuario.setGrupo(grupo);
            usuario.setAtivo(true);
            usuario.setDataCriacao(LocalDateTime.now());

            authService.salvarUsuario(usuario);

            return ResponseEntity.ok("Usuário criado com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Erro ao criar usuário: " + e.getMessage());
        }
    }
}