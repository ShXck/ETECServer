package org.etec.datastructures;


public class Node<T> {

    //What the node contains.
    private T data;
    //Reference to the next node in the list.
    private Node next;

    public Node(T data){
        this.data = data;
        this.next = null;
    }

    public T data() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Node next() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }


}
