package org.etec.sortingalgos;

import org.etec.datastructures.List;
import org.etec.management.Product;

public class Quicksort {
	
	/**
	 * Función que ordena la lista de acuerdo a las especificaciones dadas.
	 * @param order si es descendente o ascendente.
	 * @param attr el atributo por el cual se va a ordenar.
	 * @return la lista ordenada
	 */
	public static List<Product> sort(String order, List<Product> list){
		switch(order){
			case "Ascendente":
				return increasing_code_quick_sort(list, 0, list.size() - 1);
			case "Descendente":
				return decreasing_code_quick_sort(list, 0, list.size() - 1);			
		}
		return list;
	}
	
	/**
	 * Ordena los productos por precio ascendentemente.
	 * @param list la lista de productos.
	 * @param left el indice izq.
	 * @param right el indice der.
	 * @return la lista ordenada.
	 */
	private static List<Product> increasing_code_quick_sort(List<Product> list, int left, int right){

        Product pivot = (Product)list.get(left).data();
        int i = left;
        int j = right;
        Product aux;

        while (i < j){
            while (((Product)list.get(i).data()).code() <= pivot.code() && i < j) i++;
            while (((Product)list.get(j).data()).code() > pivot.code()) j--;

            if (i < j){
                aux = (Product) list.get(i).data();
                list.get(i).setData(list.get(j).data());
                list.get(j).setData(aux);
            }
        }
        list.get(left).setData(list.get(j).data());
        list.get(j).setData(pivot);
        if (left < j - 1){
            increasing_code_quick_sort(list, left, j-1);
        }
        if (j + 1 < right){
            increasing_code_quick_sort(list, j + 1, right);
        }
        return list;
    }
	
	/**
     * Ordena los productos por precio descendentemente.
     * @param list la lista de productos.
     * @param left el indice izq.
     * @param right el indice der.
     * @return la lista ordenada.
     */
    public static List<Product> decreasing_code_quick_sort(List<Product> list, int left, int right){

        Product pivot = (Product)list.get(left).data();
        int i = left;
        int j = right;
        Product aux;

        while (i < j){
            while (((Product)list.get(i).data()).code() >= pivot.code() && i < j) i++;
            while (((Product)list.get(j).data()).code() < pivot.code()) j--;

            if (i < j){
                aux = (Product) list.get(i).data();
                list.get(i).setData(list.get(j).data());
                list.get(j).setData(aux);
            }
        }
        list.get(left).setData(list.get(j).data());
        list.get(j).setData(pivot);
        if (left < j - 1){
            decreasing_code_quick_sort(list, left, j-1);
        }
        if (j + 1 < right){
            decreasing_code_quick_sort(list, j + 1, right);
        }
        return list;
    }

}
