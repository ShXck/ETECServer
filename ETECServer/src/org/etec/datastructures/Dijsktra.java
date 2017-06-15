package org.etec.datastructures;

import java.util.ArrayList;

public class Dijsktra {
	
	private int[] distance;
	
	/**
	 * Calcula la ruta más corta entre dos puntos.
	 * @param graph el grafo.
	 * @param vertex el vértice de partida.
	 * @return el arreglo con los predecesores.
	 */
    public int[] dijkstra(Graph graph, int vertex){
        distance = new int[graph.count()];
        final int[] predecessors = new int[graph.count()];
        final boolean[] visited = new boolean[graph.count()];

        for (int i = 0; i < distance.length; i++){
            distance[i] = Integer.MAX_VALUE;
        }
        distance[vertex] = 0;

        for (int i = 0; i < distance.length; i++){
            final int next = min_vertex(distance,visited);
            visited[next] = true;

            final int[] neighbors = graph.find_vertex(next).neighbors_as_array();
            for (int j = 0; j < neighbors.length; j++){
                final int v = neighbors[j];
                final int d = distance[next] + graph.find_edge(graph.find_vertex(next), graph.find_vertex(v)).weight();
                if (distance[v] > d){
                    distance[v] = d;
                    predecessors[v] = next;
                }
            }
        }
        return predecessors;
    }
    
    /**
     * Encuentra la relación más pequeña entre los vértices.
     * @param distance las distancias entre los vértices.
     * @param visited los vértices visitados.
     * @return el vértice menor.
     */
    private int min_vertex(int[] distance, boolean[] visited){
        int x = Integer.MAX_VALUE;
        int y = -1;

        for (int i = 0; i < distance.length; i++){
            if (!visited[i] && distance[i] < x){
                y = i;
                x = distance[i];
            }
        }
        return y;
    }
    
    /**
     * Busca el camino para el destino seleccionado
     * @param graph el grafo.
     * @param source el punto de salida.
     * @param destination el destino.
     * @param pred los predecesores del vertice.
     * @return la lista con el camino.
     */
    public List<String> find_path_to(Graph graph, int source, int destination, int[] pred){
        List<String> path = new List<>();
        int x = destination;
        while (x != source){
            path.addFirst(graph.find_vertex(x).label());
            x = pred[x];
        }
        path.addFirst(graph.find_vertex(source).label());

        for (int k = 0; k < path.size(); k++){
            if (path.get(k).data().equals(graph.find_vertex(destination).label())){
                return path;
            }
        }
        return null;
    }
    
    /**
     * Imprime en la consola el camino más corto.
     * @param graph el grafo.
     * @param pred los predecedores.
     * @param vertex el vértice de partida.
     * @param n los demás vértices.
     */
    public void print_path(Graph graph, int[] pred, int vertex, int n){
        final ArrayList path = new ArrayList();
        int x = n;
        while (x != vertex){
            path.add(0,graph.find_vertex(x).label());
            x = pred[x];
        }
        path.add(0,graph.find_vertex(vertex).label());
        System.out.println(path);
    }
    
    /**
     * @return las distancias más cortas.
     */
    public int[] distances(){
    	return distance;
    }
}
