public class GrafoNaoPonderado extends Grafo {

	public GrafoNaoPonderado(String nome) {
		super(nome);
		// TODO Auto-generated constructor stub
	}

    public GrafoNaoPonderado subGrafo(Lista<Vertice> listaVertice) {
        GrafoNaoPonderado novoSubGrafo = new GrafoNaoPonderado("subGrafo");
        Vertice[] listaVertices = new Vertice[this.ordem()];
        listaVertices = listaVertice.allElements(listaVertices);

        for(int i = 0; i < listaVertices.length && listaVertices[i] != null; i++) {
            novoSubGrafo.addVertice(listaVertices[i].getVertice());

            for(int j = 0; j < novoSubGrafo.ordem() - 1; j++) {
                if(this.existeAresta(listaVertices[i].getVertice(), listaVertices[j].getVertice())!=null) {
                    novoSubGrafo.addAresta(listaVertices[i].getVertice(), listaVertices[j].getVertice());
                }
            }
        }
        return novoSubGrafo;
    }

}