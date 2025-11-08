package dao;

import model.Fornecedor;
import java.sql.*; // classes JDBC
import java.util.ArrayList; // método listar
import java.util.List; // método buscar

public class FornecedorDAO {

    public void inserir(Fornecedor f) { // insere dados na tabela Fornecedor 
        String sql = "INSERT INTO FORNECEDOR (cpf_cnpj, nome, telefone, tipo_fornecedor) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConexaoBD.getConnection(); // conexão com o banco
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, f.getCpfCnpj()); // insere dados
            stmt.setString(2, f.getNome());
            stmt.setString(3, f.getTelefone());
            stmt.setString(4, f.getTipo());
            stmt.executeUpdate(); 
            System.out.println("Fornecedor cadastrado.");
        } catch (SQLException e) { // caso de erro
            System.out.println("Erro ao inserir fornecedor: " + e.getMessage());
        }
    }

    public List<Fornecedor> listar() { // lista fornecedores cadastrados
        List<Fornecedor> lista = new ArrayList<>(); // lista de objetos  do tipo fornecedor
        String sql = "SELECT * FROM FORNECEDOR";
        try (Connection conn = ConexaoBD.getConnection(); // conexão com banco
             Statement stmt = conn.createStatement(); // envia mensagem
             ResultSet rs = stmt.executeQuery(sql)) { // recebe resultado
            while (rs.next()) { // retorna linhas até se tornar falso 
                Fornecedor f = new Fornecedor();
                f.setId(rs.getInt("id_fornecedor"));
                f.setCpfCnpj(rs.getString("cpf_cnpj")); // guarda cada coluna no objeto
                f.setNome(rs.getString("nome"));
                f.setTelefone(rs.getString("telefone"));
                f.setTipo(rs.getString("tipo_fornecedor"));
                lista.add(f); // fornecedor é adicionado a lista
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar fornecedores: " + e.getMessage()); // caso de erro 
        }
        return lista; // retorna a lista completa 
    }

    public Fornecedor buscarPorId(int id) { // busca por id expecífico
        String sql = "SELECT * FROM FORNECEDOR WHERE id_fornecedor = ?";
        try (Connection conn = ConexaoBD.getConnection(); // conexão com o banco
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id); // substitui "?" pelo id
            ResultSet rs = stmt.executeQuery(); // conjunto de resultados de uma consulta do sql
            if (rs.next()) { // executa caso exista
                Fornecedor f = new Fornecedor(); 
                f.setId(rs.getInt("id_fornecedor"));
                f.setCpfCnpj(rs.getString("cpf_cnpj"));
                f.setNome(rs.getString("nome"));
                f.setTelefone(rs.getString("telefone"));
                f.setTipo(rs.getString("tipo_fornecedor"));
                return f;
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar fornecedor: " + e.getMessage()); // caso de erro
        }
        return null; // caso não encontre ninguém 
    }

    public void atualizar(Fornecedor f) { // atualiza dados existentes
        StringBuilder sql = new StringBuilder("UPDATE FORNECEDOR SET ");
        boolean primeiro = true; // controla virgula

        if (f.getNome() != null && !f.getNome().isEmpty()) {
            sql.append("nome = ?");
            primeiro = false; // atualiza caso desejado, caso contrário adiciona "?"
        }
        if (f.getTelefone() != null && !f.getTelefone().isEmpty()) {
            if (!primeiro) sql.append(", ");
            sql.append("telefone = ?");
            primeiro = false;
        }
        if (f.getTipo() != null && !f.getTipo().isEmpty()) {
            if (!primeiro) sql.append(", ");
            sql.append("tipo_fornecedor = ?");
            primeiro = false;
        }
        if (f.getCpfCnpj() != null && !f.getCpfCnpj().isEmpty()) {
            if (!primeiro) sql.append(", ");
            sql.append("cpf_cnpj = ?");
        }

        sql.append(" WHERE id_fornecedor = ?"); // controla o id

        try (Connection conn = ConexaoBD.getConnection(); // conexão com banco
             PreparedStatement stmt = conn.prepareStatement(sql.toString())) { // comando sql executado

            int idx = 1; // contador de posições 
            if (f.getNome() != null && !f.getNome().isEmpty()) stmt.setString(idx++, f.getNome()); // preenche os campos
            if (f.getTelefone() != null && !f.getTelefone().isEmpty()) stmt.setString(idx++, f.getTelefone());
            if (f.getTipo() != null && !f.getTipo().isEmpty()) stmt.setString(idx++, f.getTipo());
            if (f.getCpfCnpj() != null && !f.getCpfCnpj().isEmpty()) stmt.setString(idx++, f.getCpfCnpj());

            stmt.setInt(idx, f.getId());
            int linhas = stmt.executeUpdate();

            if (linhas > 0) {
                System.out.println("Fornecedor atualizado."); // sucesso
            } else {
                System.out.println("Nenhum fornecedor com este ID encontrado."); // erro
            }

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar fornecedor: " + e.getMessage()); // caso de erro 
        }
    }

    public void deletar(int id) { // deleta fornecedor 
        String sql = "DELETE FROM FORNECEDOR WHERE id_fornecedor = ?";
        try (Connection conn = ConexaoBD.getConnection(); // conexão com banco 
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id); // substitui "?" pelo id
            stmt.executeUpdate(); // exclui caso de certo
            System.out.println("Fornecedor deletado.");
        } catch (SQLException e) {
            System.out.println("Erro ao deletar fornecedor: " + e.getMessage()); // caso de erro
        }
    }
}
