package control;

import dao.MaterialDAO;
import model.Material;
import java.util.List;

// controlador para operações de Material
public class MaterialController {
    private MaterialDAO dao; // DAO que acessa o banco de dados

    // construtor recebe o DAO que será usado
    public MaterialController(MaterialDAO dao) {
        this.dao = dao;
    }

    // cadastrar um material
    public void cadastrar(Material m) {
        dao.inserir(m); // chama DAO para inserir no banco
    }

    // listar todos os materiais
    public List<Material> listar() {
        return dao.listar(); // chama DAO para listar do banco
    }

    // buscar material por ID
    public Material buscarPorId(int id) {
        return dao.buscarPorId(id); // retorna material ou null
    }

    // atualizar dados de um material
    public void atualizar(Material m) {
        dao.atualizar(m); // chama DAO para atualizar no banco
    }

    // deletar material pelo ID
    public void deletar(int id) {
        dao.deletar(id); // chama DAO para deletar do banco
    }
}