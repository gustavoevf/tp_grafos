import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class GrafoPonderado extends Grafo {

	public GrafoPonderado(String nome) {
		super(nome);
		// TODO Auto-generated constructor stub
	}

   
	/**
     * Adiciona uma aresta entre dois vértices do grafo com peso. 
     * Não verifica se os vértices pertencem ao grafo.
     * @param origem Vértice de origem
     * @param destino Vértice de destino
     */
    public boolean addAresta(int origem, int destino, int peso){
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
    
    public void carregar(String nomeArquivo) throws IOException{
    	//Lendo arquivo
    	BufferedReader buffRead = new BufferedReader(new FileReader("src/" + nomeArquivo));
		String linha = "";
		
		int qtdVertices = 0;
		linha = buffRead.readLine();
		
		//Obtendo quantidades de vertices do grafo
		qtdVertices = Integer.parseInt(linha.split("=")[1].trim());
		System.out.println("Quantidade de vertices: " + qtdVertices);
		
		//Obtendo os vertices do grafo
		System.out.println("Vertices:");
		for(int v = 0; v < qtdVertices; v++) {
			linha = buffRead.readLine();
			
			System.out.println(linha);
			//Adicionando vertices e removendo espaço da linha
			addVertice(Integer.parseInt(linha.trim()));
		}
		
		int qtdArestas = 0;
		linha = buffRead.readLine();
		
		System.out.println("");
		//Obtendo quantidades de vertices do grafo
		qtdArestas = Integer.parseInt(linha.split("=")[1].trim());
		System.out.println("Quantidade arestas: " + qtdArestas);
		//Obtendo os vertices do grafo
		System.out.println("Arestas:");
		for(int a = 0; a < qtdArestas; a++) {
			linha = buffRead.readLine();
			
			//Separa os 2 vertices de origem e destino para adicionar uma aresta
			int verticeOrigem = Integer.parseInt(linha.trim().split("-")[0]);
			int verticeDestino = Integer.parseInt(linha.trim().split("-")[1]);
			int peso = Integer.parseInt(linha.trim().split("-")[2]);
			
			System.out.println(verticeOrigem + " - " + verticeDestino + " - " + peso);
			addAresta(verticeOrigem, verticeDestino, peso);
		}
		buffRead.close();
    }

    public void salvar(String nomeArquivo) throws FileNotFoundException, UnsupportedEncodingException{
    	File file = new File(nomeArquivo);
        PrintWriter writer = new PrintWriter(nomeArquivo, "UTF-8");
        writer.println("----------------- " + this.nome + " -----------------");
        writer.print("Vertices do Grafo: ");
        for (Vertice vertice : this.obterVertices()) {
            writer.print(vertice.getVertice() + " ");
        }
        writer.println("");
        writer.println("Arestas do Grafo: ");
        for(int a = 0; a < this.obterVertices().length; a++) {
        	if(this.obterVertices()[a].obterArestas() != null) {
        		
        		for(Aresta ar : this.obterVertices()[a].obterArestas()) {
        			writer.println("Do vertice " + this.obterVertices()[a].getVertice() + " para o vertice "+ ar.destino() + " com o peso " + ar.peso());
        		}
        	}
        	
        }
        
        writer.close();
    }

    @Override
    public GrafoPonderado subGrafo(Lista<Vertice> listaVertice) {
        Vertice[] listaVertices = new Vertice[this.ordem()];
        listaVertices = listaVertice.allElements(listaVertices);
        GrafoPonderado novoSubGrafo = new GrafoPonderado("subGrafo");

        for(int i = 0; i < listaVertices.length && listaVertices[i] != null; i++){
            novoSubGrafo.addVertice(listaVertices[i].getVertice());

            for(int j = 0; j < novoSubGrafo.ordem() - 1; j++){
                if(this.existeAresta(listaVertices[i].getVertice(), listaVertices[j].getVertice()) != null) {
                    novoSubGrafo.addAresta(listaVertices[i].getVertice(), listaVertices[j].getVertice(), this.existeAresta(listaVertices[i].getVertice(), listaVertices[j].getVertice()).peso());
                }
            }
        }

        return novoSubGrafo;
    }
}