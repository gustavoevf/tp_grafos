public class GrafoCompleto extends Grafo {
    private int ordem;

    public GrafoCompleto(String nome, int ordem) {
        super(nome);
        this.ordem = ordem;

        for (int i = 1; i <= this.ordem; i++) {
            addVertice(i);
        }
        
        for(int v = 0; v < this.obterVertices().length; v++) {
        	 Vertice v1 = this.obterVertices()[v];
        	 for(int j = 0; j < this.obterVertices().length; j++) {
        		 Vertice v2 = this.obterVertices()[j];
        		 if(v1.getVertice() != v2.getVertice()) {
        			 addAresta(v1.getVertice(), v2.getVertice());
        		 }
            	 
            }
        }
       
    }

    @Override
    public boolean completo() {
        return true;
    }

    public Vertice existeVertice(int idVertice) {
        return this.vertices.find(idVertice);
    }


    public Aresta existeAresta(int vertice1, int vertice2) {
        Vertice saida = this.existeVertice(vertice1);
        return saida.existeAresta(vertice2);
    }
    
    public GrafoCompleto subGrafo(Lista<Vertice> vertices) {

        Vertice[] vert = null; 
        int ordemSubGrafo = vertices.allElements(vert).length;

        if(this.ordem > ordemSubGrafo) {
            GrafoCompleto subgrafo = new GrafoCompleto("Subgrafo de " + this.nome,ordemSubGrafo);
            return subgrafo;

        } else{
            GrafoCompleto subgrafo = new GrafoCompleto("Subgrafo de " + this.nome,this.ordem);
            return subgrafo;
        }
    }
}