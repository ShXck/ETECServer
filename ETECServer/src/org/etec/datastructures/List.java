package org.etec.datastructures;


public class List<T> {

    //Node at the top of the list
    private Node head;

    //Size of the structure
    private int size;

    public List(){

        this.head = null;
        this.size = 0;
    }

    /**
     * Checks if the list is empty
     * @return wether or not the list is empty.
     */
    public boolean isEmpty(){
        return this.head == null;
    }

    /**
     * Adds the new node at the top of the list.
     * @param data Node information.
     */
    public void addFirst(T data){

        Node newNode = new Node(data);

        if(isEmpty()){
            setHead(newNode);
        }else{
            newNode.setNext(peek());
            setHead(newNode);
        }
        size++;
    }

    /**
     * Adds the new node at the end of the list.
     * @param data Node information.
     */
    public void addLast(T data){

        Node newNode = new Node(data);

        if(isEmpty()){
            setHead(newNode);
        }else{
            Node current = this.head;

            while(current.next() != null){
                current = current.next();
            }
            current.setNext(newNode);
        }
        size++;
    }

    /**
     * Removes the actual head.
     */
    public void removeHead(){
        head = head.next();
        size--;
    }

    /**
     * Prints the content of the list
     */
    public void printList() {
        Node current = this.head;
        while (current != null) {
            System.out.print(current.data() + " ");
            current = current.next();
        }
    }

    public boolean contains(T element){

        Node current = this.head;

        if (isEmpty()){
            return false;
        }

        while (current.next() != null){
            if(current.data() == element || current.equals(element)){
                return true;
            }else{
                current = current.next();
            }
        }
        return false;
    }

    public Node peek() {
        return head;
    }

    public int size() {
        return size;
    }

    public void setHead(Node head) {
        this.head = head;
    }
    
    public void clear(){
    	head = null;
    }


    public Node get(int posicion){

        Node current = head;
        
        if (posicion < 0 || posicion > size){
            throw new IndexOutOfBoundsException("La posición indicada no existe");
        }

        int i = 0;

        while (i < posicion){
            current = current.next();
            i++;
        }

        return current;
    }

    public void remove(T data){

        if (data.equals(head.data())){
            head = head.next();
        }else {

            Node<T> current = head;

            while (current != null) {
                if (current.next().data().equals(data)) {
                    break;
                } else {
                    current = current.next();
                }
            }
            current.setNext(current.next().next());
        }
        size--;
    }

    public List<T> reverse(){

        List<T> reversed_list = new List<T>();

        for (int i = this.size - 1; i >= 0; i--){
            Node<T> current = get(i);
            reversed_list.addLast(current.data());
        }
        return reversed_list;
    }
}
