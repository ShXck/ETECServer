package org.etec.sortingalgos;

import org.etec.datastructures.List;
import org.etec.management.Product;

public class BubbleSort {
	
	/**
	 * Función que ordena la lista de acuerdo a las especificaciones dadas.
	 * @param order si es descendente o ascendente.
	 * @param attr el atributo por el cual se va a ordenar.
	 * @return la lista ordenada
	 */
	public static List<Product> sort(String order, List<Product> list){
		switch(order){
			case "Ascendente":
				return increasing_code_bubble_sort(list);
			case "Descendente":
				return decreasing_code_bubble_sort(list);		
		}
		return list;
	}
	
	/**
	 * Ordena productos por su precio en forma ascendente.
	 * @param list la lista de productos.
	 * @return la lista ordenada.
	 */
	private static List<Product> increasing_code_bubble_sort(List<Product> list){

        int tamaño = list.size();

        Product temp;

        for (int i = 0; i < (tamaño - 1); i++){
            for (int j = 0; j < (tamaño - i - 1); j++){
                if (((Product)list.get(j + 1).data()).code() < ((Product)list.get(j).data()).code()){
                    temp = (Product) list.get(j).data();
                    list.get(j).setData(list.get(j+1).data());
                    list.get(j+1).setData(temp);
                }
            }
        }
        return list;
    }
	
	/**
	 * Ordena productos por su precio en forma descendente.
	 * @param list la lista de productos.
	 * @return la lista ordenada.
	 */
	private static List<Product> decreasing_code_bubble_sort(List<Product> list){

        int tamaño = list.size();

        Product temp;

        for (int i = 0; i < (tamaño - 1); i++){
            for (int j = 0; j < (tamaño - i - 1); j++){
                if (((Product)list.get(j + 1).data()).code() > ((Product)list.get(j).data()).code()){   //((String)list.get(j+1).data()).compareTo((String)list.get(j).data()) < 0
                    temp = (Product) list.get(j).data();
                    list.get(j).setData(list.get(j+1).data());
                    list.get(j+1).setData(temp);
                }
            }
        }
        return list;
    }
}
