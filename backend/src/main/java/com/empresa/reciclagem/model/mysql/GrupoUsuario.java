package com.empresa.reciclagem.model.mysql;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "grupo_usuarios")
public class GrupoUsuario {

    @Id
    @Column(name = "id_grupo", length = 10)
    private String idGrupo;

    @Column(name = "nome_grupo", nullable = false, unique = true)
    private String nomeGrupo;

    @Column(nullable = false)
    private String descricao;

    @Column(nullable = false)
    private Integer prioridade;

    @OneToMany(mappedBy = "grupo")
    private List<Usuario> usuarios;

    // Getters e Setters
    public String getIdGrupo() {
        return idGrupo;
    }

    public void setIdGrupo(String idGrupo) {
        this.idGrupo = idGrupo;
    }

    public String getNomeGrupo() {
        return nomeGrupo;
    }

    public void setNomeGrupo(String nomeGrupo) {
        this.nomeGrupo = nomeGrupo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(Integer prioridade) {
        this.prioridade = prioridade;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }
}
