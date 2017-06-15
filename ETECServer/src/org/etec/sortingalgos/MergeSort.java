package org.etec.sortingalgos;

import org.etec.datastructures.List;
import org.etec.management.Product;

public class MergeSort {
	
	/**
	 * Función que ordena la lista de acuerdo a las especificaciones dadas.
	 * @param order si es descendente o ascendente.
	 * @param attr el atributo por el cual se va a ordenar.
	 * @return la lista ordenada
	 */
	public static List<Product> sort(String order, List<Product> list){
		switch(order){
			case "Ascendente":
				return increasing_code_merge_sort(list, 0, list.size() - 1);
			case "Descendente":
				return decreasing_code_merge_sort(list, 0, list.size() - 1);			
		}
		return list;
	}
	
	/**
	 * Ordena los productos por precio en forma ascendente.
	 * @param list la lista de productos.
	 * @param low el indice izq.
	 * @param high el indice der.
	 * @return la lista ordenada.
	 */
	private static List<Product> increasing_code_merge_sort(List<Product> list, int low, int high) {

        if (low < high){
            int middle = (low + high)/2;
            increasing_code_merge_sort(list, low, middle);
            increasing_code_merge_sort(list, middle + 1, high);
            increased_merge_products(low, middle, high, list);
        }
        return list;
    }

    /**
     * Método auxiliar para unir la lista.
     * @param low el indice izq
     * @param middle el indice del medio.
     * @param high el indice der.
     * @param list la lista por unir.
     */
    private static void increased_merge_products(int low, int middle, int high, List<Product> list) {

        List<Product> list_aux = new List<>();
        add_some_products(list_aux, list);

        for (int i = low; i <= high; i++) {
            list_aux.get(i).setData(list.get(i).data());
        }

        int i = low;
        int j = middle + 1;
        int k = low;
        while (i <= middle && j <= high) {
            if (((Product) list_aux.get(i).data()).code() <= ((Product) list_aux.get(j).data()).code()) {
                list.get(k).setData(list_aux.get(i).data());
                i++;
            } else {
                list.get(k).setData(list_aux.get(j).data());
                j++;
            }
            k++;
        }
        while (i <= middle) {
            list.get(k).setData(list_aux.get(i).data());
            k++;
            i++;
        }
    }
    
    /**
     * Ordena la lista por precio de forma descendente.
     * @param list la lista desordenada.
     * @param low el indice izq.
     * @param high el indice der.
     * @return la lista ordenada.
     */
    private static List<Product> decreasing_code_merge_sort(List<Product> list, int low, int high) {

        if (low < high){
            int middle = (low + high)/2;
            decreasing_code_merge_sort(list, low, middle);
            decreasing_code_merge_sort(list, middle + 1, high);
            decreased_merge_products(low, middle, high, list);
        }
        return list;
    }

    /**
     * Método auxiliar para unir la lista.
     * @param low el indice izq
     * @param middle el indice del medio.
     * @param high el indice der.
     * @param list la lista por unir.
     */
    private static void decreased_merge_products(int low, int middle, int high, List<Product> list) {

        List<Product> list_aux = new List<>();
        add_some_products(list_aux, list);

        for (int i = low; i <= high; i++) {
            list_aux.get(i).setData(list.get(i).data());
        }

        int i = low;
        int j = middle + 1;
        int k = low;
        while (i <= middle && j <= high) {
            if (((Product) list_aux.get(i).data()).code() >= ((Product) list_aux.get(j).data()).code()) {
                list.get(k).setData(list_aux.get(i).data());
                i++;
            } else {
                list.get(k).setData(list_aux.get(j).data());
                j++;
            }
            k++;
        }
        while (i <= middle) {
            list.get(k).setData(list_aux.get(i).data());
            k++;
            i++;
        }
    }
    
    
    /**
     * Inicializa la lista auxiliar.
     * @param list la lista auxiliar.
     * @param base_list la lista base.
     */
    private static void add_some_products(List list, List base_list){
        for (int i = 0; i < base_list.size(); i++) {
            list.addLast(new Product(" NAME",i, i + 10));
        }
    }

}
