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

public class Vertice {
    
    private ABB<Aresta> arestas;    
    private final int  id;
    private boolean visitado;

/**
     * Construtor para criação de vértice identificado
     * @param id Número/id do vértice a ser criado (atributo final).
     */
    public Vertice(int id){
        this.id = id;
        this.arestas = new ABB<Aresta>();
        this.visitado = false;
    }
    
    public boolean addAresta(int destino, double peso) {
        return this.arestas.add(destino, new Aresta(peso, destino));
    }
    
    public Aresta[] obterArestas() {
    	return arestas.allElements(new Aresta[this.arestas.size()]);
    }
    
    public int getVertice(){
        return this.id;

    }

    public boolean removerAresta(int key) {
        return this.arestas.remove(key);
    }

    public boolean removerAresta() {
        this.arestas = new ABB<>();
        return true;
    }

    public Aresta existearesta(int idVertice) {
        Aresta [] arestas1 = obterArestas();
        for (int i =0; i < arestas1.length; i++) {
            if (arestas1[i].destino() == idVertice) {
                return arestas1[i];
            }
        }
        return null;
    }
    
    /**
     * Retorna o grau do vértice
     * @return Grau (inteiro não negativo)
     */
    public int grau(){
        return this.arestas.size();
    }

    public void visitar(){
        this.visitado = true;
    }
    
    public void limparVisita(){
        this.visitado = false;
    }
    
    public boolean visitado(){
        return this.visitado;
    }
}
