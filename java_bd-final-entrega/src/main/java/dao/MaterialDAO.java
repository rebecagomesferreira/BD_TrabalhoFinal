package dao;

import model.Material;
import java.sql.*; // classes JDBC
import java.util.ArrayList; // método listar
import java.util.List; // método buscar

public class MaterialDAO {

    public void inserir(Material m) { // cadastra novo material
        String sql = "INSERT INTO MATERIAL (tipo, descricao, preco_kg) VALUES (?, ?, ?)";
        try (Connection conn = ConexaoBD.getConnection(); // conexão com banco
             PreparedStatement stmt = conn.prepareStatement(sql)) { // envia comando ao sql
            stmt.setString(1, m.getTipo()); // insere dados
            stmt.setString(2, m.getDescricao());
            stmt.setDouble(3, m.getPrecoKg());
            stmt.executeUpdate();
            System.out.println("Material cadastrado.");
        } catch (SQLException e) {
            System.out.println("Erro ao inserir material: " + e.getMessage()); // caso de erro
        }
    }

    public List<Material> listar() { // lista materiais
        List<Material> lista = new ArrayList<>(); // lista objetos do tipo material
        String sql = "SELECT * FROM MATERIAL";
        try (Connection conn = ConexaoBD.getConnection(); // conexão com o banco
             Statement stmt = conn.createStatement(); // envia mensagem
             ResultSet rs = stmt.executeQuery(sql)) { // recebe resultado
            while (rs.next()) { // lista até se torna falso
                Material m = new Material(); // objeto tipo material
                m.setId(rs.getInt("id_material")); // listagem
                m.setTipo(rs.getString("tipo"));
                m.setDescricao(rs.getString("descricao"));
                m.setPrecoKg(rs.getDouble("preco_kg"));
                lista.add(m); // adiciona a lista
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar materiais: " + e.getMessage()); // caso de erro
        }
        return lista; // retorna a lista completa
    }

    public Material buscarPorId(int id) { // busca material pelo id
        String sql = "SELECT * FROM MATERIAL WHERE id_material = ?";
        try (Connection conn = ConexaoBD.getConnection(); // conexão com o banco
             PreparedStatement stmt = conn.prepareStatement(sql)) { // envia requisição ao banco
            stmt.setInt(1, id); // substitui "?" pelo id
            ResultSet rs = stmt.executeQuery(); // conjunto de resultados de uma consulta do sql
            if (rs.next()) { // consulta caso exista
                Material m = new Material(); // objeto do tipo material
                m.setId(rs.getInt("id_material"));
                m.setTipo(rs.getString("tipo"));
                m.setDescricao(rs.getString("descricao"));
                m.setPrecoKg(rs.getDouble("preco_kg"));
                return m;
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar material: " + e.getMessage()); // caso de erro
        }
        return null; // caso não exista retorna null
    }

    public void atualizar(Material m) { // atualiza material
        StringBuilder sql = new StringBuilder("UPDATE MATERIAL SET ");
        boolean primeiro = true; // controla as vírgulas 

        if (m.getTipo() != null && !m.getTipo().isEmpty()) {
            sql.append("tipo = ?");
            primeiro = false; // atualiza caso necessário 
        }
        if (m.getDescricao() != null && !m.getDescricao().isEmpty()) {
            if (!primeiro) sql.append(", ");
            sql.append("descricao = ?");
            primeiro = false;
        }
        if (m.getPrecoKg() >= 0) {
            if (!primeiro) sql.append(", ");
            sql.append("preco_kg = ?");
        }

        sql.append(" WHERE id_material = ?"); // condição pelo id

        try (Connection conn = ConexaoBD.getConnection(); // conexão com o banco
             PreparedStatement stmt = conn.prepareStatement(sql.toString())) { // comando sql executado

            int idx = 1; // conta posições 
            if (m.getTipo() != null && !m.getTipo().isEmpty()) stmt.setString(idx++, m.getTipo()); // preenche
            if (m.getDescricao() != null && !m.getDescricao().isEmpty()) stmt.setString(idx++, m.getDescricao());
            if (m.getPrecoKg() >= 0) stmt.setDouble(idx++, m.getPrecoKg());
            stmt.setInt(idx, m.getId()); // comando sql

            int linhas = stmt.executeUpdate();
            if (linhas > 0) { // caso o contador for maior que 0 atualizada as linhas desejadas
                System.out.println("Material atualizado.");
            } else {
                System.out.println("Nenhum material encontrado com esse ID."); // erro de id
            }
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar material: " + e.getMessage()); // caso de erro
        }
    }

    public void deletar(int id) { // deleta material
        String sql = "DELETE FROM MATERIAL WHERE id_material = ?";
        try (Connection conn = ConexaoBD.getConnection(); // conexão com o banco
             PreparedStatement stmt = conn.prepareStatement(sql)) { // comando sql
            stmt.setInt(1, id); // captura do id
            stmt.executeUpdate(); // exclui caso de certo
            System.out.println("Material deletado.");
        } catch (SQLException e) {
            System.out.println("Erro ao deletar material: " + e.getMessage()); // caso de erro
        }
    }

    // --- Novos métodos: integração com VIEW e PROCEDURE ---
    /**
     * Lista materiais a partir de uma VIEW do MySQL (ex: vw_materiales).
     * Retorna lista de Material construídos a partir das colunas da view.
     */
    public List<Material> listarPorView() {
        List<Material> lista = new ArrayList<>();
        String sql = "SELECT id_material, tipo, descricao, preco_kg FROM vw_materiales"; // ajuste o nome da view conforme seu BD
        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Material m = new Material();
                m.setId(rs.getInt("id_material"));
                m.setTipo(rs.getString("tipo"));
                m.setDescricao(rs.getString("descricao"));
                m.setPrecoKg(rs.getDouble("preco_kg"));
                lista.add(m);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar materiais da view: " + e.getMessage());
        }
        return lista;
    }

    /**
     * Chama uma procedure que conta materiais e retorna o total.
     * Exemplo de procedure no MySQL:
     * DELIMITER //
     * CREATE PROCEDURE sp_contar_materiais(OUT total INT)
     * BEGIN
     *   SELECT COUNT(*) INTO total FROM material;
     * END //
     * DELIMITER ;
     */
    public int contarMateriaisProcedure() {
        String call = "{ call sp_contar_materiais(?) }"; // ajuste o nome conforme sua procedure
        try (Connection conn = ConexaoBD.getConnection();
             CallableStatement cs = conn.prepareCall(call)) {
            cs.registerOutParameter(1, java.sql.Types.INTEGER);
            cs.execute();
            return cs.getInt(1);
        } catch (SQLException e) {
            System.out.println("Erro ao chamar procedure sp_contar_materiais: " + e.getMessage());
            return -1;
        }
    }

}
