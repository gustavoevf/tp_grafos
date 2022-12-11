import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.*;

/** 
 * MIT License
 *
 * Copyright(c) 2021 João Caram <caram@pucminas.br>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

/** 
 * Classe básica para um Grafo simples
 */
public class Grafo {
    public final String nome;
    protected ABB<Vertice> vertices;

    /**
     * Construtor. Cria um grafo vazio com capacidade para MAX_VERTICES
     */
    public Grafo(String nome){
        this.nome = nome;
        this.vertices = new ABB<>();
    }


    /**
     * Adiciona, se possível, um vértice ao grafo. O vértice é auto-nomeado com o próximo id disponível.
     */
    public boolean addVertice(int id){
        Vertice novo = new Vertice(id);
        return this.vertices.add(id, novo);
    }

    /**
     * Adiciona uma aresta entre dois vértices do grafo. 
     * Não verifica se os vértices pertencem ao grafo.
     * @param origem Vértice de origem
     * @param destino Vértice de destino
     */
    public boolean addAresta(int origem, int destino){
        boolean adicionou = false;
        Vertice saida = this.existeVertice(origem);
        Vertice chegada = this.existeVertice(destino);
        if(saida!=null && chegada !=null){
            saida.addAresta(destino);
            chegada.addAresta(origem);
            adicionou = true;
        }
        
        return adicionou;

    }

    public void buscaEmProfundidade(int inicio) {
        boolean[] visitados = new boolean[this.vertices.size()];
        buscaEmProfundidadeRecursiva(inicio, visitados);
    }

    private void buscaEmProfundidadeRecursiva(int atual, boolean[] visitados) {
        System.out.print(atual + " ");
        visitados[atual] = true;
        this.vertices.find(atual).visitar();
        for (Aresta destino : this.vertices.find(atual).obterArestas()) {
            if (!visitados[destino.destino()])
                buscaEmProfundidadeRecursiva(destino.destino(), visitados);
        }
    }

    public void buscaEmLargura(int v)
    {
        boolean visitados[] = new boolean[this.vertices.size()];

        // Create a queue for BFS
        LinkedList<Integer> lista = new LinkedList<Integer>();

        // Mark the current node as visited and enqueue it
        visitados[v] = true;
        this.vertices.find(v).visitar();
        lista.add(v);

        while (lista.size() != 0)
        {
            v = lista.poll();
            System.out.print(v + " ");


            Iterator<Aresta> i = Arrays.stream(this.vertices.find(v).obterArestas()).iterator();

            while (i.hasNext())
            {
                Aresta a = i.next();
                if (!visitados[a.destino()])
                {
                    visitados[a.destino()] = true;
                    this.vertices.find(a.destino()).visitar();
                    lista.add(a.destino());
                }
            }
        }
    }

    public void caminhoEntreDoisVertices(int v1, int v2)
    {
        boolean visitados[] = new boolean[this.vertices.size()];

        // Create a queue for BFS
        LinkedList<Integer> lista = new LinkedList<Integer>();

        // Mark the current node as visited and enqueue it
        visitados[v1] = true;
        this.vertices.find(v1).visitar();
        lista.add(v1);

        boolean achou = false;
        while (lista.size() != 0)
        {
            if(achou){
                break;
            }

            v1 = lista.poll();
            System.out.print(v1 + " ");

            if(v1 == v2){
                break;
            }


            Iterator<Aresta> i = Arrays.stream(this.vertices.find(v1).obterArestas()).iterator();

            while (i.hasNext())
            {
                Aresta a = i.next();
                if (!visitados[a.destino()])
                {
                    visitados[a.destino()] = true;
                    this.vertices.find(a.destino()).visitar();
                    lista.add(a.destino());
                }
            }
        }
    }

    public void subtrairVertice(Vertice[] vertices) {
        if(vertices != null) {
            for (int i = 0; i < vertices.length; i++) {
                subtrairVertice(vertices[i]);
            }
        }
    }

    public void subtrairVertice(Vertice vertice) {
        if(vertice != null) {
            this.vertices.remove(vertice.getVertice());
            subtrairAresta(vertice.getVertice());
        }
    }


    public void subtrairAresta(int idVerticeA, int idVerticeB) {
        if(this.vertices.find(idVerticeA) != null) {
            Aresta[] arestas = this.vertices.find(idVerticeA).obterArestas();
            Aresta aresta = Arrays.stream(arestas).filter(x -> x.destino() == idVerticeB).findAny().orElse(null);
            this.vertices.find(idVerticeA).removerAresta(aresta.destino());
        }

        if(this.vertices.find(idVerticeB) != null) {
            Aresta[] arestas = this.vertices.find(idVerticeB).obterArestas();
            Aresta aresta = Arrays.stream(arestas).filter(x -> x.destino() == idVerticeA).findAny().orElse(null);
            this.vertices.find(idVerticeB).removerAresta(aresta.destino());
        }
    }

    public void subtrairAresta(int idVertice) {
        if(this.vertices.find(idVertice) != null) {
            this.vertices.find(idVertice).removerAresta();
        }

        for (int i =0; i < vertices.size(); i++) {
            Vertice vertice = this.vertices.find(i);
            if(vertice != null) {
                Aresta[] arestas = this.vertices.find(vertice.getVertice()).obterArestas();
                Aresta aresta = Arrays.stream(arestas).filter(x -> x.destino() == idVertice).findAny().orElse(null);
                this.vertices.find(vertice.getVertice()).removerAresta(idVertice);
            }
        }
    }

    public Vertice existeVertice(int idVertice){
        return this.vertices.find(idVertice);
    }

    public Aresta existeAresta(int verticeA, int verticeB){
    	return vertices.find(verticeA).existeAresta(verticeB);
    }
    
    public Vertice[] obterVertices() {
    	return vertices.allElements(new Vertice[this.ordem()]);
    }
    
    public int tamanho(){
        return 0;
    }

    public int ordem(){
        return this.vertices.size();
    }
}
