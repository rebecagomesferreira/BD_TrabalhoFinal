package com.empresa.reciclagem.model.mysql;

import jakarta.persistence.*;

@Entity
@Table(name = "grupo_usuarios")
public class GrupoUsuario {

    @Id
    @Column(name = "id_grupo", length = 10)
    private String idGrupo;

    @Column(name = "nome_grupo", length = 50, nullable = false) // Mude para 50
    private String nomeGrupo;

    @Column(name = "descricao", columnDefinition = "TEXT", nullable = false) // Mude para TEXT e NOT NULL
    private String descricao;

    @Column(name = "prioridade", nullable = false) // ADICIONE ESTE CAMPO
    private Integer prioridade;

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
}