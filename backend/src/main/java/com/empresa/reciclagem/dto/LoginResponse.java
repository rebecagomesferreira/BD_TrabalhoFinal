package com.empresa.reciclagem.dto;

public class LoginResponse {
    private boolean autenticado;
    private String mensagem;
    private String grupo;

    public LoginResponse(boolean autenticado, String mensagem, String grupo) {
        this.autenticado = autenticado;
        this.mensagem = mensagem;
        this.grupo = grupo;
    }

    // Getters e setters
    public boolean isAutenticado() {
        return autenticado;
    }

    public void setAutenticado(boolean autenticado) {
        this.autenticado = autenticado;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }
}
