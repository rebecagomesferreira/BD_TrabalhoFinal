package com.empresa.reciclagem.controller;

import com.empresa.reciclagem.model.mysql.Usuario;
import com.empresa.reciclagem.model.mysql.GrupoUsuario; // ← ADICIONE ESTE IMPORT
import com.empresa.reciclagem.repository.mysql.GrupoUsuarioRepository; // ← ADICIONE ESTE IMPORT
import com.empresa.reciclagem.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final GrupoUsuarioRepository grupoUsuarioRepository; // ← JÁ ESTÁ AQUI

    @GetMapping
    public ResponseEntity<List<Usuario>> listarTodos() {
        return ResponseEntity.ok(usuarioService.listarTodos());
    }

    @PostMapping
    public ResponseEntity<Usuario> criar(@RequestBody Usuario usuario) {
        try {
            // Verifica se o grupo foi enviado e carrega o GrupoUsuario completo
            if (usuario.getGrupo() != null && usuario.getGrupo().getIdGrupo() != null) {
                String idGrupo = usuario.getGrupo().getIdGrupo();

                // Busca o GrupoUsuario completo do banco
                GrupoUsuario grupoCompleto = grupoUsuarioRepository.findById(idGrupo)
                        .orElseThrow(() -> new RuntimeException("Grupo não encontrado: " + idGrupo));

                usuario.setGrupo(grupoCompleto);
            } else {
                throw new RuntimeException("Grupo é obrigatório");
            }

            // Define data de criação
            usuario.setDataCriacao(java.time.LocalDateTime.now());

            // Garante que o usuário esteja ativo
            if (usuario.getAtivo() == null) {
                usuario.setAtivo(true);
            }

            Usuario usuarioSalvo = usuarioService.salvar(usuario);
            return ResponseEntity.status(HttpStatus.CREATED).body(usuarioSalvo);

        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarPorId(@PathVariable Integer id) {
        Optional<Usuario> usuario = usuarioService.buscarPorId(id);
        return usuario.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> atualizar(@PathVariable Integer id, @RequestBody Usuario novoUsuario) {
        return usuarioService.buscarPorId(id)
                .map(u -> {
                    u.setNomeUsuario(novoUsuario.getNomeUsuario());
                    u.setGrupo(novoUsuario.getGrupo());
                    u.setAtivo(novoUsuario.getAtivo());
                    return ResponseEntity.ok(usuarioService.salvar(u));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Usuario> toggleStatus(@PathVariable Integer id, @RequestBody Boolean ativo) {
        Optional<Usuario> usuarioOpt = usuarioService.buscarPorId(id);
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            usuario.setAtivo(ativo);
            Usuario usuarioAtualizado = usuarioService.salvar(usuario);
            return ResponseEntity.ok(usuarioAtualizado);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        usuarioService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}