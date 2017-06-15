package org.etec.searchalgos;

import org.etec.datastructures.List;
import org.etec.management.Product;

public class BinarySearch {
	
	/**
	 * Busca un elemento en la lista de productos.
	 * @param list la lista 
	 * @param to_find el elemento por encontrar.
	 * @param order el ordend e ordenamiento.
	 * @return el indice donde esta el elemento.
	 */
	public static int search(List<Product> list, int to_find, String order){
		if (order.equals("Ascendente")) {
			return increased_binary_search(list, to_find);
		}else {
			return decreased_binary_search(list, to_find);
		}
	}
	
	/**
     * Método de Búsqueda Binaria descendente.
     * @param list la lista ordenada.
     * @param to_find el elemento por buscar.
     * @return la posición del elemento en la lista.
     */
    private static int decreased_binary_search(List<Product> list, int to_find){
        int start = 0;
        int end = list.size() - 1;
        while (start <= end) {
            int mid = (start + end) / 2;
            if (to_find == ((Product) list.get(mid).data()).code()) {
                return mid;
            }
            if (to_find > ((Product) list.get(mid).data()).code()) {
                end = mid - 1;
            } else {
                start = mid + 1;
            }
        }
        return -1;
    }
    
    /**
     * Método de Búsqueda binaria ascendente.
     * @param list la lista ordenada.
     * @param to_find el elemento por buscar.
     * @return la posición del elemento en la lista.
     */
    private static int increased_binary_search(List<Product> list, int to_find){
        int start = 0;
        int end = list.size() - 1;
        while (start <= end) {
            int mid = (start + end) / 2;
            if (to_find == ((Product) list.get(mid).data()).code()) {
                return mid;
            }
            if (to_find < ((Product) list.get(mid).data()).code()) {
                end = mid - 1;
            } else {
                start = mid + 1;
            }
        }
        return -1;
    }
}
