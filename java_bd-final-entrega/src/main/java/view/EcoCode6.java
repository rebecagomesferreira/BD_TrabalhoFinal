package view; // define o pacote da view

//Imports dos pacotes 
import control.FornecedorController;
import control.MaterialController;
import dao.FornecedorDAO;
import dao.MaterialDAO;
import model.Fornecedor;
import model.Material;

import java.util.List; 
import java.util.Scanner; 

public class EcoCode6 { // declara a classe principal da view

    private static final Scanner sc = new Scanner(System.in); // cria o objeto scanner para ler entradas
    private static final FornecedorController fornecedorCtrl = new FornecedorController(new FornecedorDAO()); // cria o controlador de fornecedor
    private static final MaterialController materialCtrl = new MaterialController(new MaterialDAO()); // cria o controlador de material

    public static void main(String[] args) { // método que inicia o programa
        int opcao; 

        System.out.println("Bem-vindo ao EcoCode!"); 

         //Loop inicial
        do { 
            System.out.println("\n===== MENU PRINCIPAL ====="); 
            System.out.println("1 - Gerenciar fornecedores"); 
            System.out.println("2 - Gerenciar materiais"); 
            System.out.println("0 - Sair"); 
            System.out.print("Escolha , "); 
            opcao = sc.nextInt();
            sc.nextLine(); // pega a quebra de linha

            //verificação da opção escolhida
            switch (opcao) { 
                //chamadas
                case 1:
                    menuFornecedores();
                    break;
                case 2:
                    menuMateriais();
                    break;
                case 0:
                    System.out.println("Adeus!");
                    break;
                default:
                    System.out.println("Opcao invalida.");
                    break;
            }

        } while (opcao != 0); // repete até o usuário digitar 0

        sc.close(); // fecha o scanner ao sair
    } // fim do método iniciar

    //menu fornecedores
    private static void menuFornecedores() { 
        int opcao; 
        do {
            System.out.println("\n=== MENU FORNECEDORES ==="); 
            System.out.println("1 - Cadastrar"); 
            System.out.println("2 - Listar"); 
            System.out.println("3 - Atualizar");
            System.out.println("4 - Excluir"); 
            System.out.println("0 - Voltar"); 
            System.out.print("Escolha , "); 
            opcao = sc.nextInt(); // lê o número da opção escolhido
            sc.nextLine(); 

            switch (opcao) { 
                case 1:
                    cadastrarFornecedor();
                    break;
                case 2:
                    listarFornecedores();
                    break;
                case 3:
                    atualizarFornecedor();
                    break;
                case 4:
                    excluirFornecedor();
                    break;
                case 0:
                    System.out.println("Voltando!");
                    break;
                default:
                    System.out.println("Opcao invalida.");
                    break;
            }

        } while (opcao != 0); 
    } // fim do menu de fornecedores

    private static void cadastrarFornecedor() { // método para cadastrar fornecedor
        System.out.println("\n---- CADASTRAR FORNECEDOR----"); 
        System.out.print("CPF/CNPJ , "); 
        String cpfCnpj = sc.nextLine(); 
        System.out.print("Nome , ");
        String nome = sc.nextLine(); 
        System.out.print("Telefone , "); 
        String telefone = sc.nextLine();
        System.out.print("Tipo (pessoa fisica / juridica) , "); 
        String tipo = sc.nextLine(); 

        fornecedorCtrl.cadastrar(new Fornecedor(cpfCnpj, nome, telefone, tipo));
        System.out.println("Fornecedor cadastrado com sucesso!");
    } // fim do cadastro de fornecedor

    private static void listarFornecedores() { // método para listar fornecedores
        System.out.println("\n---- LISTA DE FORNECEDORES ----");
        List<Fornecedor> lista = fornecedorCtrl.listar(); 
        if (lista.isEmpty()) { // verifica se está vazia
            System.out.println("Ainda nao tem fornecedor cadastrado."); 
        } else {
            lista.forEach(System.out::println); // imprime cada fornecedor
        }
    } 

    private static void atualizarFornecedor() { // método para atualizar fornecedor
        System.out.println("\n---- ATUALIZAR FORNECEDOR ----"); 
        System.out.print("ID do fornecedor , "); 
        int id = sc.nextInt(); 
        sc.nextLine(); 

        Fornecedor f = fornecedorCtrl.buscarPorId(id); // busca fornecedor pelo id
        if (f == null) { // verifica se existe
            System.out.println("Fornecedor nao encontrado."); 
            return; // sai do método
        }

        System.out.print("Novo nome (enter para manter) , "); 
        String nome = sc.nextLine(); 
        System.out.print("Novo telefone (enter para manter) , ");
        String telefone = sc.nextLine(); 
        System.out.print("Novo tipo (enter para manter) , "); 
        String tipo = sc.nextLine(); 
        System.out.print("Novo cpf/cnpj (enter para manter) , "); 
        String cpfCnpj = sc.nextLine(); 

        //atualizações 
        if (!nome.isEmpty()) f.setNome(nome); 
        if (!telefone.isEmpty()) f.setTelefone(telefone); 
        if (!tipo.isEmpty()) f.setTipo(tipo); 
        if (!cpfCnpj.isEmpty()) f.setCpfCnpj(cpfCnpj);

        fornecedorCtrl.atualizar(f); 
        System.out.println("Atualizacao feita."); 
    } // fim da atualização de fornecedor

    private static void excluirFornecedor() { // método para excluir fornecedor
        System.out.print("\nID do fornecedor a excluir , "); 
        int id = sc.nextInt();
        sc.nextLine(); 
        fornecedorCtrl.deletar(id); // chama método de exclusão
        System.out.println("Fornecedor removido (se existia)."); 
    } // fim da exclusão de fornecedor

    private static void menuMateriais() { // menu de materiais
        int opcao; // variável de controle
        do {
            System.out.println("\n=== MENU MATERIAIS ==="); 
            System.out.println("1 - Cadastrar"); 
            System.out.println("2 - Listar"); 
            System.out.println("3 - Atualizar"); 
            System.out.println("4 - Excluir"); 
            System.out.println("0 - Voltar");
            System.out.print("Escolha , "); 
            opcao = sc.nextInt(); 
            sc.nextLine(); 

            switch (opcao) { 
                case 1:
                    cadastrarMaterial();
                    break;
                case 2:
                    listarMateriais();
                    break;
                case 3:
                    atualizarMaterial();
                    break;
                case 4:
                    excluirMaterial();
                    break;
                case 0:
                    System.out.println("Voltando!");
                    break;
                default:
                    System.out.println("Opcao invalida.");
                    break;
            }

        } while (opcao != 0); // repete até sair
    } // fim do menu materiais

    private static void cadastrarMaterial() { // método para cadastrar material
        System.out.println("\n---- CADASTRAR MATERIAL ----"); 
        System.out.print("Tipo , "); 
        String tipo = sc.nextLine(); 
        System.out.print("Descricao , ");
        String descricao = sc.nextLine(); 
        System.out.print("Preco por kg , "); 
        double preco = sc.nextDouble(); 
        sc.nextLine(); 

        materialCtrl.cadastrar(new Material(tipo, descricao, preco)); // cadastra material
        System.out.println("Material cadastrado com sucesso!"); 
    } // fim do cadastro de material

    private static void listarMateriais() { // método para listar materiais
        System.out.println("\n ---- LISTA DE MATERIAIS ----"); 
        List<Material> lista = materialCtrl.listar(); // obtém lista
        if (lista.isEmpty()) { // verifica se vazia
            System.out.println("Nenhum material registrado."); 
        } else {
            lista.forEach(System.out::println); // imprime cada material 
        }
    } // fim da listagem de materiais

    private static void atualizarMaterial() { // método para atualizar material
        System.out.println("\n---- ATUALIZAR MATERIAL ----");
        System.out.print("ID do material , ");
        int id = sc.nextInt();
        sc.nextLine(); 

        Material m = materialCtrl.buscarPorId(id); // busca material
        if (m == null) { // verifica se existe
            System.out.println("Material nao encontrado."); 
            return; // sai do método
        }

        System.out.print("Novo tipo (enter para manter) , "); 
        String tipo = sc.nextLine(); 
        System.out.print("Nova descricao (enter para manter) , "); 
        String desc = sc.nextLine(); 
        System.out.print("Novo preco por kg (-1 para manter) , "); 
        double preco = sc.nextDouble();
        sc.nextLine();

        if (!tipo.isEmpty()) m.setTipo(tipo); 
        if (!desc.isEmpty()) m.setDescricao(desc); 
        if (preco >= 0) m.setPrecoKg(preco); 

        materialCtrl.atualizar(m); // salva 
        System.out.println("Material atualizado."); 
    } // fim da aualização 

    private static void excluirMaterial() { // método para excluir material
        System.out.print("\n ID do material a excluir , "); 
        int id = sc.nextInt(); 
        sc.nextLine(); 
        materialCtrl.deletar(id); //exclui
        System.out.println("Material removido (se existia)."); 
    } // fim da exclusão 
} // fim da classe 
