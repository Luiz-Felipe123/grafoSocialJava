package grafos;

import java.util.Scanner;
import java.util.List;

public class Grafos {

    static int menuMetodos() {
        Scanner scanner = new Scanner(System.in);
        int op;
        System.out.println("1-Adicionar Novo Perfil (nó)");
        System.out.println("2-Adicionar ou Remover Conexões (arestas)");
        System.out.println("3-Listar Contatos de um Membro");
        System.out.println("4-Verificar Alcance de um Membro");
        System.out.println("5-Salvar Dados em Arquivo");
        System.out.println("6-Carregar Dados de Arquivo");
        System.out.println("7-Visualizar Perfil por ID:");
        System.out.println("0-Sair");
        op = scanner.nextInt();
        return op;
    }

    public static void main(String[] args) {
        Graph grafo = new Graph();
        Scanner scanner = new Scanner(System.in);
        int op = 0;
        grafo.carregarGrafo();
        do {
            op = menuMetodos();
            switch (op) {
                case 1:
                    System.out.println("Nome do Perfil: ");
                    String nome = scanner.next();
                    int novoID = grafo.gerarNovoID();
                    System.out.println("Interesses do Perfil:");
                    String interesses = scanner.next();
                    Perfil perfil = new Perfil(nome, interesses);
                    grafo.adicionarVertice(novoID, perfil);
                    break;
                case 2:
                    System.out.println("1-Adicionar Conexao | 2-Remover Conexao");
                    int escolhaConexao = scanner.nextInt();
                    if (escolhaConexao == 1) {
                        System.out.println("Origem (ID):");
                        int origem = scanner.nextInt();
                        System.out.println("Destino (ID):");
                        int destino = scanner.nextInt();
                        grafo.adicionarConexao(origem, destino);
                    } else if (escolhaConexao == 2) {
                        System.out.println("Origem (ID):");
                        int origem = scanner.nextInt();
                        System.out.println("Destino (ID):");
                        int destino = scanner.nextInt();
                        grafo.removerConexao(origem, destino);
                    } else {
                        System.out.println("Opção inválida.");
                    }
                    grafo.imprimirGrafo();
                    break;
                case 3:
                    System.out.println("ID do Membro:");
                    int idMembro = scanner.nextInt();
                    List<Integer> contatos = grafo.listarContatos(idMembro);
                    System.out.println("Contatos de " + idMembro + ": " + contatos);
                    break;
                case 4:
                    System.out.println("ID da Origem:");
                    int origem = scanner.nextInt();
                    System.out.println("ID do Destino:");
                    int destino = scanner.nextInt();
                    List<Integer> alcance = grafo.alcance(origem);
                    if (alcance.contains(destino))
                        System.out.println("Existe caminho");
                    else
                        System.out.println("Não existe caminho");
                    break;
                case 5:
                    grafo.salvarGrafo();
                    System.out.println("Dados salvos em arquivo.");
                    break;
                case 6:
                    grafo.carregarGrafo();
                    System.out.println("Dados carregados do arquivo.");
                    break;
                 case 7: // Adicione uma opção para visualizar o perfil por ID
                    System.out.println("ID do Perfil:");
                    int idPerfil = scanner.nextInt();
                    Perfil perfilVisualizado = grafo.obterPerfilPeloID(idPerfil);
                    if (perfilVisualizado != null) {
                        System.out.println("Perfil do ID " + idPerfil + ":");
                        System.out.println("Nome: " + perfilVisualizado.getNome());
                        System.out.println("Interesses: " + perfilVisualizado.getInteresses());
                    } else {
                        System.out.println("Perfil não encontrado para o ID " + idPerfil);
                    }
                    break;
                case 0:
                    System.out.println("Saindo");
                    break;
                default:
                    System.out.println("Opção inválida.");
                    break;
            }// fim switch
        } while (op != 0);
    }

    private static void exibirPerfilPorID(int idPerfil) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
