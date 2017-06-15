package org.etec.sortingalgos;

import org.etec.datastructures.List;
import org.etec.management.Product;

public class InsertionSort {
	
	/**
	 * Función que ordena la lista de acuerdo a las especificaciones dadas.
	 * @param order si es descendente o ascendente.
	 * @param attr el atributo por el cual se va a ordenar.
	 * @return la lista ordenada
	 */
	public static List<Product> sort(String order, List<Product> list){
		switch(order){
			case "Ascendente":
				return increasing_code_insertion_sort(list);
			case "Descendente":
				return decreasing_code_insertion_sort(list);			
		}
		return list;
	}
	
	/**
	 * Ordena productos por su precio en forma ascendente.
	 * @param list la lista de productos.
	 * @return la lista ordenada.
	 */
	private static List<Product> increasing_code_insertion_sort(List<Product> list){

        for (int i = 0; i < list.size(); i++){
            Product temp = (Product) list.get(i).data();
            int j;
            for (j = i - 1; j >= 0 && temp.code() < ((Product) list.get(j).data()).code(); j--){
                list.get(j + 1).setData(list.get(j).data());
            }
            list.get(j + 1).setData(temp);
        }
        return list;
    }
	
	/**
	 * Ordena productos por su precio en forma descendente.
	 * @param list la lista de productos.
	 * @return la lista ordenada.
	 */
	private static List<Product> decreasing_code_insertion_sort(List<Product> list){

        for (int i = 0; i < list.size(); i++){
            Product temp = (Product) list.get(i).data();
            int j;
            for (j = i - 1; j >= 0 && temp.code() > ((Product) list.get(j).data()).code(); j--){
                list.get(j + 1).setData(list.get(j).data());
            }
            list.get(j + 1).setData(temp);
        }
        return list;
    }
}
