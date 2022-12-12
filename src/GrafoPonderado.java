import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.List;

public class GrafoPonderado extends Grafo {

	public GrafoPonderado(String nome) {
		super(nome);
		// TODO Auto-generated constructor stub
	}

	private City[] cidades;

   
	/**
     * Adiciona uma aresta entre dois vértices do grafo com peso. 
     * Não verifica se os vértices pertencem ao grafo.
     * @param origem Vértice de origem
     * @param destino Vértice de destino
     */
    public boolean addAresta(int origem, int destino, double peso){
        boolean adicionou = false;
        Vertice saida = this.existeVertice(origem);
        Vertice chegada = this.existeVertice(destino);
        if(saida!=null && chegada !=null){
            saida.addAresta(destino, peso);
            chegada.addAresta(origem, peso);
            adicionou = true;
        }
        
        return adicionou;

    }

	public void preencher(String nomeArquivo) throws FileNotFoundException {
		Gson gson = new Gson();
		cidades = gson.fromJson(new FileReader(nomeArquivo), City[].class);

		Double[][] distancias = new Double[cidades.length][cidades.length];
		//inclui todos os vértices (cidades)
		for (int i = 0; i < cidades.length; i++) {
			addVertice(i);
		}

		//itera sobre as cidades e cria as arestas correspondentes, avaliando a distância
		for (int i = 0; i < cidades.length; i++) {
			Integer[] cidadesProximas = new Integer[4];
			int insercoes = 0;
			for (int j = 0; j < cidades.length; j++) {
				if(j < i) {
					distancias[i][j] = distancias[j][i];
				} else {
					distancias[i][j] = cidades[i].distancia(cidades[j]);
				}

				//para cada possível destino, verifica se já possui as 4 conexões
				if(existeVertice(j).obterArestas().length < 4) {
					if (insercoes < 4) {
						Integer indexNulo = null;
						for(int k = 0, encontrou = 0; k < cidadesProximas.length && encontrou == 0; k++) {
							encontrou = cidadesProximas[k] == null ? 1 : 0;
							indexNulo = k;
						}
						cidadesProximas[indexNulo] = j;
						insercoes++;
					} else {
						for (int k = 0, alterou = 0; k < cidadesProximas.length && alterou == 0; k++) {
							if (cidadesProximas[k] == null || distancias[i][j] < distancias[i][cidadesProximas[k]] || distancias[i][cidadesProximas[k]] == 0) {
								cidadesProximas[k] = j;
								alterou = 1;
							}
						}
					}
				}
			}

			for (int j = 0; j < cidadesProximas.length && existeVertice(i).obterArestas().length < 4; j++) {
				if(cidadesProximas[j] != null) {
					addAresta(i, cidadesProximas[j], distancias[i][j]);
				}
			}
		}
	}

	public void print() {
		System.out.println("----------------- " + this.nome + " -----------------");
		System.out.print("Vertices do Grafo: ");
		for (Vertice vertice : this.obterVertices()) {
			System.out.println(vertice.getVertice() + " " + cidades[vertice.getVertice()].city);
		}
		System.out.println("");
		System.out.println("Arestas do Grafo: ");
		for(int a = 0; a < this.obterVertices().length; a++) {
			if(this.obterVertices()[a].obterArestas() != null) {

				for(Aresta ar : this.obterVertices()[a].obterArestas()) {
					System.out.println("Do vertice " + this.obterVertices()[a].getVertice() + " para o vertice "+ ar.destino() + " com o peso " + ar.peso());
				}
			}

		}
	}

	public void dijkstra(int idVertice)
	{

	}

}