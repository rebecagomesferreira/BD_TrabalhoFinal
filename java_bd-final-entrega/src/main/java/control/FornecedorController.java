package control;

import dao.FornecedorDAO;
import model.Fornecedor;
import java.util.List;

// controlador para operações de Fornecedor
public class FornecedorController {
    private FornecedorDAO dao; // DAO que acessa o banco de dados

    // construtor recebe o DAO que será usado
    public FornecedorController(FornecedorDAO dao) {
        this.dao = dao;
    }

    // cadastrar um fornecedor
    public void cadastrar(Fornecedor f) {
        dao.inserir(f); // chama DAO para inserir no banco
    }

    // listar todos os fornecedores
    public List<Fornecedor> listar() {
        return dao.listar(); // chama DAO para listar do banco
    }

    // buscar fornecedor por ID
    public Fornecedor buscarPorId(int id) {
        return dao.buscarPorId(id); // retorna fornecedor ou null
    }

    // atualizar dados de um fornecedor
    public void atualizar(Fornecedor f) {
        dao.atualizar(f); // chama DAO para atualizar no banco
    }

    // deletar fornecedor pelo ID
    public void deletar(int id) {
        dao.deletar(id); // chama DAO para deletar do banco
    }
}