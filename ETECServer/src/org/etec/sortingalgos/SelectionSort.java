package org.etec.sortingalgos;

import org.etec.datastructures.List;
import org.etec.management.Product;

public class SelectionSort {
	
	/**
	 * Función que ordena la lista de acuerdo a las especificaciones dadas.
	 * @param order si es descendente o ascendente.
	 * @param attr el atributo por el cual se va a ordenar.
	 * @return la lista ordenada
	 */
	public static List<Product> sort(String order, List<Product> list){
		switch(order){
			case "Ascendente":
				return increasing_code_selection_sort(list);
			case "Descendente":
				return decreasing_code_selection_sort(list);
		}
		return list;
	}
	
	/**
	 * Ordena productos por su precio en forma ascendente.
	 * @param list la lista de productos.
	 * @return la lista ordenada.
	 */
	private static List<Product> increasing_code_selection_sort(List<Product> list){

        int min_index;
        Product temp;

        for (int i = 0; i < (list.size() - 1); i++){
            min_index = i;
            for (int j = i + 1; j < list.size(); j++){
                if (((Product)list.get(min_index).data()).code() > ((Product)list.get(j).data()).code()){
                    min_index = j;
                }
            }
            if (min_index != i){
                temp = (Product) list.get(i).data();
                list.get(i).setData(list.get(min_index).data());
                list.get(min_index).setData(temp);
            }
        }
        return list;
    }
	
	
	/**
	 * Ordena productos por su precio de forma descendente.
	 * @param list la lista de productos.
	 * @return la lista ordenada.
	 */
	private static List<Product> decreasing_code_selection_sort(List<Product> list){

        int min_index;
        Product temp;

        for (int i = 0; i < (list.size() - 1); i++){
            min_index = i;
            for (int j = i + 1; j < list.size(); j++){
                if (((Product)list.get(min_index).data()).code() < ((Product)list.get(j).data()).code()){
                    min_index = j;
                }
            }
            if (min_index != i){
                temp = (Product) list.get(i).data();
                list.get(i).setData(list.get(min_index).data());
                list.get(min_index).setData(temp);
            }
        }
        return list;
    }
}
