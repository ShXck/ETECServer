package org.etec.datastructures;

public class TransitiveClosure {
	
    private int vertexes;
    private boolean[][] transitive_closure;

    /**
     * Encuentra el cierre transitivo de un grafo.
     * @param matrix la matriz de adyacencia que representa al grafo.
     */
    public void transitive_closure(int[][] matrix){
        this.vertexes = matrix.length;
        transitive_closure = new boolean[vertexes][vertexes];
        for (int i = 0; i < vertexes; i++)
        {
            for (int j = 0; j < vertexes; j++)
                if (matrix[i][j] != 0)
                    transitive_closure[i][j] = true;
            transitive_closure[i][i] = true;
        }
        for (int i = 0; i < vertexes; i++)
        {
            for (int j = 0; j < vertexes; j++)
            {
                if (transitive_closure[j][i])
                    for (int k = 0; k < vertexes; k++)
                        if (transitive_closure[j][i] && transitive_closure[i][k])
                            transitive_closure[j][k] = true;
            }
        }
    }

    /**
     * Muestra la relación de los vértices.
     */
    public void display(){
        System.out.println("\nCierre transitivo:\n");
        System.out.print(" ");
        for (int v = 0; v < vertexes; v++)
            System.out.print("   V" + v );
        System.out.println();
        for (int v = 0; v < vertexes; v++)
        {
            System.out.print("V" + v +" ");
            for (int w = 0; w < vertexes; w++)
            {
                if (transitive_closure[v][w])
                    System.out.print("  1  ");
                else
                    System.out.print("  0  ");
            }
            System.out.println();
        }
    }

}
