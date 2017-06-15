package org.etec.datastructures;

import org.etec.datastructures.List;
import org.etec.datastructures.Node;

public class Graph<G> {

    private List<Vertex<G>> adjacency_list;
    private List<Edge> edges;
    private int current_id;

    public Graph(){
        this(new List<>(), new List<>());
    }

    public Graph(List<Vertex<G>> vertexes, List<Edge> edges){
        this.adjacency_list = vertexes;
        this.edges = edges;
        this.current_id = 0;
    }

    /**
     * A√±ade un v√©rtice al grafo.
     * @param element el elemento por agregar.
     */
    @SuppressWarnings("unchecked")
    public void add_vertex(G element, String label){
        if (element == null){
            throw new IllegalArgumentException("Element is null");
        }
        adjacency_list.addLast(new Vertex<G>(element, label, current_id++));
    }

    /**
     * A√±de un vertice al grafo.
     * @param vertex el v√©rtice por agregar.
     */
    public void add_vertex(Vertex<G> vertex){
        if (vertex == null){
            throw new IllegalArgumentException("Vertex is null");
        }
        adjacency_list.addLast(vertex);
        current_id++;
    }

    /**
     * A√±ade una arista al grafo.
     * @param from el vertice donde sale la arista.
     * @param to el destino de la arista.
     * @param weight el peso de la arista.
     */
    public void add_edge(Vertex<G> from, Vertex<G> to, int weight){

        List<Vertex> neighbors = from.neighbors();

        if (from == null || to == null){
            throw new IllegalArgumentException("Vertex value is null");
        }
        edges.addLast(new Edge(weight,from,to));
        neighbors.addLast(to);
    }

    /**
     * @param from nodo inicial.
     * @param to nodo destino.
     * @return si los v√©rtices est√°n conectados.
     */
    @SuppressWarnings("unchecked")
    public boolean are_connected(Vertex<G> from, Vertex<G> to){

        Node<Vertex> current = from.neighbors().peek();

        while (current != null){
            if (current.data() == to.data() || current.data().equals(to)){
                return true;
            }else {
                current = current.next();
            }
        }
        return false;
    }
    
    /**
     * Convierte el grafo en una matriz de adyacencia
     * @return la matriz que representa al grafo.
     */
    public int[][] graph_to_matrix(){

        int[][] matrix = new int[count()][count()];

        for (int i = 0; i < count(); i++){
            for (int k = 0; k < count(); k++){
                if (are_connected(i,k)) {
                    matrix[i][k] = 1;
                }else {
                    matrix[i][k] = 0;
                }
            }
        }
        return matrix;
    }
    
    /**
     * Determina si dos vÈrtices est·n conectados dados sus ids.
     * @param from el id de la fuente.
     * @param to el id del destino
     * @return si los vÈrtices est·n conectados.
     */
    public boolean are_connected(int from, int to){

        Node<Vertex> current = find_vertex(from).neighbors().peek();
        Vertex<G> to_vertex = find_vertex(to);

        while (current != null){
            if (current.data().id() == to_vertex.id()){
                return true;
            }else {
                current = current.next();
            }
        }
        return false;
    }

    /**
     * Encuentra un vÈrtice en el grafo.
     * @param id la id del vertice.
     * @return el vÈrtice que contiene el objeto.
     */
    public Vertex<G> find_vertex(int id){

        for (int i = 0; i < adjacency_list.size(); i++){
            if (((Vertex)adjacency_list.get(i).data()).id() == id){
                return (Vertex<G>) adjacency_list.get(i).data();
            }
        }
        return null;
    }
    
    /**
     * Encuentra un vÈrtice en el grafo dada la etiqueta.
     * @param label la etiqueta.
     * @return el vÈrtice que contiene el objeto.
     */
    public Vertex<G> find_vertex(String label){
    	for(int i = 0; i < adjacency_list.size(); i++){
    		Vertex<G> current = (Vertex<G>)adjacency_list.get(i).data();
    		if (current.label().equals(label)) {
				return current;
			}
    	}
    	return null;
    }

    /**
     * @return la cantidad de v√©rtices en el grafo.
     */
    public int count(){
        return adjacency_list.size();
    }

    /**
     * @param from Nodo de inicio.
     * @param to destino.
     * @return la arista que conecta los dos v√©rtices.
     */
    public Edge find_edge(Vertex<G> from, Vertex<G> to){

        if (are_connected(from,to)){

            Node<Edge> current = edges.peek();

            while (current != null){
                if (current.data().from().equals(from) && current.data().to().equals(to)){
                    return current.data();
                }else {
                    current = current.next();
                }
            }
        }
        return null;
    }

    /**
     * Elimina una arista.
     * @param edge la arista a eliminar.
     */
    public void remove_edge(Edge edge){
        edge.from().neighbors().remove(edge.to());
        this.edges.remove(edge);
    }

    public List<Vertex<G>> vertexes(){
        return this.adjacency_list;
    }

    public List<Edge> edges(){
        return this.edges;
    }
}
