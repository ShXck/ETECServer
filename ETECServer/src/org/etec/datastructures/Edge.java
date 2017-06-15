package org.etec.datastructures;

public class Edge {

    private int weight;
    private Vertex from;
    private Vertex to;

    public Edge(int weight, Vertex from, Vertex to){
        this.weight = weight;
        this.from = from;
        this.to = to;
    }

    public Edge(int weight){
        this(weight,null,null);
    }

    public void connect(Vertex from, Vertex to){
        this.from = from;
        this.to = to;
    }

    public int weight(){
        return this.weight;
    }

    public Vertex from(){
        return this.from;
    }

    public Vertex to(){
        return this.to;
    }

    public void change_weight(int new_weight){
        this.weight = new_weight;
    }

}
