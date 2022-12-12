import java.util.*;

public class DijkstraAlgorithm {

    private final List<Vertice> nodes;
    private final List<Aresta> edges;
    private Set<Vertice> settledNodes;
    private Set<Vertice> unSettledNodes;
    private Map<Vertice, Vertice> predecessors;
    private Map<Vertice, Double> distance;

    public DijkstraAlgorithm(GrafoPonderado graph) {
        // create a copy of the array so that we can operate on this array
        this.nodes = new ArrayList<Vertice>(Arrays.stream(graph.obterVertices()).toList());
        this.edges = new ArrayList<Aresta>();
        for(int a = 0; a < graph.obterVertices().length; a++) {
            if(graph.obterVertices()[a].obterArestas() != null) {

                for(Aresta ar : graph.obterVertices()[a].obterArestas()) {
                    this.edges.add(ar);
                }
            }

        }
    }

    public void execute(Vertice source, GrafoPonderado graph) {
        settledNodes = new HashSet<Vertice>();
        unSettledNodes = new HashSet<Vertice>();
        distance = new HashMap<Vertice, Double>();
        predecessors = new HashMap<Vertice, Vertice>();
        distance.put(source, (double)0);
        unSettledNodes.add(source);
        while (unSettledNodes.size() > 0) {
            Vertice node = getMinimum(unSettledNodes);
            settledNodes.add(node);
            unSettledNodes.remove(node);
            findMinimalDistances(node, graph);
        }
    }

    private void findMinimalDistances(Vertice node, GrafoPonderado graph) {
        List<Vertice> adjacentNodes =  graph.vizinhos(node.getVertice());
        for (Vertice target : adjacentNodes) {
            if (getShortestDistance(target) > getShortestDistance(node)
                    + getDistance(node, target)) {
                distance.put(target, getShortestDistance(node)
                        + getDistance(node, target));
                predecessors.put(target, node);
                unSettledNodes.add(target);
            }
        }

    }

    private double getDistance(Vertice node, Vertice target) {
        for (Aresta edge : node.obterArestas()) {
            if (edge.destino() == target.getVertice()) {
                return edge.peso();
            }
        }
        throw new RuntimeException("Should not happen");
    }

    private Vertice getMinimum(Set<Vertice> vertexes) {
        Vertice minimum = null;
        for (Vertice vertex : vertexes) {
            if (minimum == null) {
                minimum = vertex;
            } else {
                if (getShortestDistance(vertex) < getShortestDistance(minimum)) {
                    minimum = vertex;
                }
            }
        }
        return minimum;
    }

    private boolean isSettled(Vertice vertex) {
        return settledNodes.contains(vertex);
    }

    private double getShortestDistance(Vertice destination) {
        Double d = distance.get(destination);
        if (d == null) {
            return Integer.MAX_VALUE;
        } else {
            return d;
        }
    }

    /*
     * This method returns the path from the source to the selected target and
     * NULL if no path exists
     */
    public LinkedList<Vertice> getPath(Vertice target) {
        LinkedList<Vertice> path = new LinkedList<Vertice>();
        Vertice step = target;
        // check if a path exists
        if (predecessors.get(step) == null) {
            return null;
        }
        path.add(step);
        while (predecessors.get(step) != null) {
            step = predecessors.get(step);
            path.add(step);
        }
        // Put it into the correct order
        Collections.reverse(path);
        return path;
    }

}