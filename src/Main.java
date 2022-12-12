import com.google.gson.Gson;

import java.io.FileReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;


public class Main {

    public static void main(String[] args) throws java.io.FileNotFoundException {
        GrafoPonderado grafo = new GrafoPonderado("grafoCidades");

        // Preenchimento seguindo os critérios estabelecidos
        grafo.preencher("data/br.json");

        // Listagem
        grafo.print();
        System.out.println("");
        System.out.println("");

        // Busca em profundidade
        System.out.println("------------------------Busca em profundidade------------------------");
        grafo.buscaEmProfundidade(0);
        System.out.println("");
        System.out.println("");

        // Busca em largura
        System.out.println("------------------------Busca em largura------------------------");
        grafo.buscaEmLargura(0);
        System.out.println("");
        System.out.println("");

        // Caminho entre dois vertices
        System.out.println("------------------------Caminho entre dois vertices------------------------");
        grafo.caminhoEntreDoisVertices(0, 104);
        System.out.println("");
        System.out.println("");

        // Subtração de Vértice 50
        System.out.println("------------------------Subtracao de vertice------------------------");
        grafo.subtrairVertice(grafo.obterVertices()[50]);
        System.out.println("");
        grafo.print();
        System.out.println("");
        System.out.println("");


        // Substração de 1 Aresta do vertice 20
        System.out.println("------------------------Subtracao de aresta------------------------");
        Vertice v = grafo.obterVertices()[20];
        grafo.subtrairAresta(20, v.obterArestas()[0].destino());
        grafo.print();
        System.out.println("");
        System.out.println("");

        //Grau de vertice e vizinhos
        System.out.println("------------------------Grau vertice e vizinhos------------------------");
        grafo.grauVizinhos(10);
        System.out.println("");
        System.out.println("");

        // Distância mínima
        System.out.println("------------------------Caminho minimo do 30 ao 0------------------------");
        DijkstraAlgorithm djikstra = new DijkstraAlgorithm(grafo);
        djikstra.execute(grafo.obterVertices()[30], grafo);
        LinkedList<Vertice> caminhoMinimo = djikstra.getPath(grafo.obterVertices()[0]);
        for(int i = 0; i < caminhoMinimo.size(); i++) {
            System.out.println(caminhoMinimo.get(i).getVertice());
        }
    }
}
