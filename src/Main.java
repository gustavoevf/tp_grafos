import com.google.gson.Gson;

import java.io.FileReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;


public class Main {

    public static void main(String[] args) throws java.io.FileNotFoundException, UnsupportedEncodingException {
        Gson gson = new Gson();
        City[] cidades = gson.fromJson(new FileReader("data/br.json"), City[].class);

        GrafoPonderado grafo = new GrafoPonderado("grafoCidades");
        Double[][] distancias = new Double[cidades.length][cidades.length];
        //inclui todos os vértices (cidades)
        for (int i = 0; i < cidades.length; i++) {
            grafo.addVertice(i);
        }

        //itera sobre as cidades
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
                if(grafo.existeVertice(j).obterArestas().length < 4) {
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

            for (int j = 0; j < cidadesProximas.length && grafo.existeVertice(i).obterArestas().length < 4; j++) {
                if(cidadesProximas[j] != null) {
                    grafo.addAresta(i, cidadesProximas[j], distancias[i][j]);
                }
            }
        }

        grafo.salvar("grafoResult");
        for (int i = 0; i < cidades.length; i++) {
            if (grafo.existeVertice(i).obterArestas().length != 4) {
//                System.out.println(i + " " + grafo.existeVertice(i).obterArestas().length);
            }
        }

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


    }
}
