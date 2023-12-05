package grafos;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class Graph {
    private Map<Integer, LinkedList<Aresta>> meuGrafo;
    private Map<Integer, Perfil> perfis = new HashMap<>();
    private int ultimoID;

    public Graph() {
        this.meuGrafo = new HashMap<>();
        this.perfis = new HashMap<>();
        this.ultimoID = 0;
    }

    // Altere o método adicionarVertice na classe Graph para aceitar um perfil
    public void adicionarVertice(int vertice, Perfil perfil) {
        if (!meuGrafo.containsKey(vertice)) {
            meuGrafo.put(vertice, new LinkedList<>());
            perfis.put(vertice, perfil);
        }
    }

    // Método para gerar um novo ID único
    public int gerarNovoID() {
        return ++ultimoID;
    }
    
    public void adicionarPerfil(int id, String nome, String interesses) {
        perfis.put(id, new Perfil(nome, interesses));
    }
    
    // Adicione um método para obter o perfil associado a um ID
    public Perfil obterPerfilPeloID(int id) {
        return perfis.get(id);
    }

    public String obterNomePeloID(int id) {
        return perfis.get(id).getNome();
    }

    public String obterInteressesPeloID(int id) {
        return perfis.get(id).getInteresses();
    }

    // Adiciona uma conexão entre dois perfis
    public void adicionarConexao(int origem, int destino) {
    if (meuGrafo.containsKey(origem) && meuGrafo.containsKey(destino) && origem != destino) {
        // Verificar se a conexão já existe antes de adicionar
        if (!meuGrafo.get(origem).contains(new Aresta(destino))) {
            meuGrafo.get(origem).add(new Aresta(destino));
            meuGrafo.get(destino).add(new Aresta(origem));
        }
    }
}

    // Remove uma conexão entre dois perfis
    public void removerConexao(int origem, int destino) {
        if (meuGrafo.containsKey(origem) && meuGrafo.containsKey(destino)) {
            meuGrafo.get(origem).removeIf(aresta -> aresta.vertice == destino);
            meuGrafo.get(destino).removeIf(aresta -> aresta.vertice == origem);
        }
    }
    
    public void adicionarAresta(int origem, int destino, int peso) {
    if (!meuGrafo.containsKey(origem) || !meuGrafo.containsKey(destino)) {
        throw new IllegalArgumentException("Os vértices de origem e destino devem existir no grafo.");
    }

    meuGrafo.get(origem).add(new Aresta(destino));
    meuGrafo.get(destino).add(new Aresta(origem));
}

    // Lista os IDs dos contatos de um membro
    public List<Integer> listarContatos(int idMembro) {
    List<Integer> contatos = new ArrayList<>();
    if (meuGrafo.containsKey(idMembro)) {
        contatos.clear(); // Limpar a lista antes de adicionar novos contatos
        for (Aresta aresta : meuGrafo.get(idMembro)) {
            contatos.add(aresta.vertice);
        }
    }
    return contatos;
    }


    // Método para verificar alcance de um membro
    public List<Integer> alcance(int origem) {
        Set<Integer> visitados = new HashSet<>();
        List<Integer> alcance = new ArrayList<>();
        explorarAlcance(origem, visitados, alcance);
        return alcance;
    }
    
    public void exibirPerfilPorID(int id) {
        if (perfis.containsKey(id)) {
            Perfil perfil = perfis.get(id);
            System.out.println("ID: " + id);
            System.out.println("Nome: " + perfil.getNome());
            System.out.println("Interesses: " + perfil.getInteresses());
        } else {
            System.out.println("Perfil com ID " + id + " não encontrado.");
        }
    }


    private void explorarAlcance(int verticeAtual, Set<Integer> visitados, List<Integer> alcance) {
        visitados.add(verticeAtual);
        alcance.add(verticeAtual);

        LinkedList<Aresta> adjacentes = meuGrafo.get(verticeAtual);
        if (adjacentes != null) {
            for (Aresta adjacente : adjacentes) {
                if (!visitados.contains(adjacente.vertice)) {
                    explorarAlcance(adjacente.vertice, visitados, alcance);
                }
            }
        }
    }
    
    public void imprimirPerfis() {
        for (Map.Entry<Integer, Perfil> entry : perfis.entrySet()) {
            int id = entry.getKey();
            Perfil perfil = entry.getValue();
            System.out.println(id + " - " + perfil.getNome() + " - " + perfil.getInteresses());
        }
    }

    // Método para imprimir o grafo
    public void imprimirGrafo() {
        for (Map.Entry<Integer, LinkedList<Aresta>> entry : meuGrafo.entrySet()) {
        int id = entry.getKey();
        System.out.println("Perfil " + id + ":");
        System.out.println("Nome: " + obterNomePeloID(id));
        System.out.println("Interesses: " + obterInteressesPeloID(id));

        LinkedList<Aresta> vizinhos = entry.getValue();
        System.out.print("Conexões: ");
        for (Aresta vizinho : vizinhos) {
            System.out.print(vizinho.vertice + " ");
        }
        System.out.println("\n");
        }
    }
    
        
    public void salvarPerfis() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("perfis.txt"))) {
            for (Map.Entry<Integer, Perfil> entry : perfis.entrySet()) {
                int id = entry.getKey();
                Perfil perfil = entry.getValue();
                writer.println(id + " - " + perfil.getNome() + " - " + perfil.getInteresses());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método para salvar o grafo em um arquivo
    // Modifique o método salvarGrafo para incluir informações dos perfis
    public void salvarGrafo() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("grafo_social.txt"))) {
            for (Map.Entry<Integer, LinkedList<Aresta>> entry : meuGrafo.entrySet()) {
                int vertice = entry.getKey();
                LinkedList<Aresta> vizinhos = entry.getValue();
                for (Aresta vizinho : vizinhos) {
                    writer.println(vertice + " " + vizinho.vertice + " " + vizinho.peso);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        salvarPerfis();
    }

    public void carregarGrafo() {
        
        carregarPerfis();

        try (Scanner scanner = new Scanner(new File("grafo_social.txt"))) {
            while (scanner.hasNext()) {
                int origem = scanner.nextInt();
                int destino = scanner.nextInt();
                int peso = scanner.nextInt();

                adicionarVertice(origem, new Perfil("Nome padrão", "Interesses padrão"));
                adicionarVertice(destino, new Perfil("Nome padrão", "Interesses padrão"));
                adicionarAresta(origem, destino, peso);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void carregarPerfis() {
        try (Scanner scanner = new Scanner(new File("perfis.txt"))) {
            while (scanner.hasNextLine()) {
                String linha = scanner.nextLine();
                String[] partes = linha.split(" - ");
                int id = Integer.parseInt(partes[0]);
                String nome = partes[1];
                String interesses = partes[2];

                perfis.put(id, new Perfil(nome, interesses));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}

