package org.etec.datastructures;


import org.etec.datastructures.List;
import org.etec.datastructures.Node;
import org.etec.management.Package;

public class Vertex<V> {

    private V data;
    private List<Vertex> neighbors;
    private String label;
    int id;

    public Vertex(V data, String label, int id){
        this.data = data;
        this.neighbors = new List<>();
        this.label = label;
        this.id = id;
    }

    public V data(){
        return this.data;
    }

    public List<Vertex> neighbors(){
        return this.neighbors;
    }

    public void print_neighbors(){

        Node<Vertex> current = neighbors.peek();

        while (current != null){
            System.out.print(current.data().data + " - ");
            current = current.next();
        }
    }

    public int count(){
        return neighbors.size();
    }

    public boolean is_neighbor(Vertex vertex){

        Node<Vertex> current = neighbors.peek();

        while (current != null){
            if (current.data().equals(vertex)){
                return true;
            }else {
                current = current.next();
            }
        }
        return false;
    }

    public String  label(){
        return this.label;
    }

    public int id(){
        return this.id;
    }

    public void set_data(V data){
        this.data = data;
    }

    public int[] neighbors_as_array(){

        int[] neighbors = new int[this.neighbors.size()];

        for (int i = 0; i < neighbors.length; i++){
            neighbors[i] = ((Vertex)this.neighbors.get(i).data()).id();
        }
        return neighbors;
    }
    
    @Override
    public boolean equals(Object object){
    	if (object == this) {
			return true;
		}
		
		if (!(object instanceof Vertex)) {
			return false;
		}
		
		Vertex vertex = (Vertex)object;
		return Integer.compare(vertex.id(), this.id) == 0;
    }

}
