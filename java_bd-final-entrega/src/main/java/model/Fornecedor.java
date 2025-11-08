package model;

public class Fornecedor {
    private int id;
    private String cpfCnpj;
    private String nome;
    private String telefone;
    private String tipo;

    // construtores
    public Fornecedor () {}
    public Fornecedor(String cpfCnpj, String nome, String telefone, String tipo) { 
        this.cpfCnpj = cpfCnpj;
        this.nome = nome;
        this.telefone = telefone;
        this.tipo = tipo;
    }

    // getters e setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getCpfCnpj() { return cpfCnpj; }
    public void setCpfCnpj(String cpfCnpj) { this.cpfCnpj = cpfCnpj; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    @Override
    // exibe a saída
    public String toString() {
        return String.format("\n%d:\n%s\n%s\n%s\n%s\n", id, nome, cpfCnpj, telefone, tipo);
    }
}
