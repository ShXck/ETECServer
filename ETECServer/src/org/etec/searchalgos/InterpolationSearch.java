package org.etec.searchalgos;

import org.etec.datastructures.List;
import org.etec.management.Product;

public class InterpolationSearch {
	
	/**
	 * Busca un elemento en la lista de productos.
	 * @param list la lista 
	 * @param to_find el elemento por encontrar.
	 * @param order el ordend e ordenamiento.
	 * @return el indice donde esta el elemento.
	 */
	public static int search(List<Product> list, int to_find, String order){
		if (order.equals("Ascendente")) {
			return increased_interpolation_search(list, to_find);
		}else {
			return decreased_interpolation_search(list, to_find);
		}
	}

	 /**
     * Método para buscar por interpolación ascendentemente.
     * @param to_find el elemento por buscar.
     * @param list la lista ordenada.
     * @return la posición del elemento dentro de la lista.
     */
    private static int increased_interpolation_search(List<Product> list, int to_find){

        int low = 0;
        int high = list.size() - 1;
        int mid;
        while (((Product)list.get(low).data()).code() <= to_find && ((Product)list.get(high).data()).code() >= to_find)
        {
            if ((((Product)list.get(high).data()).code() - ((Product)list.get(low).data()).code()) == 0)
                return (low + high)/2;

            mid = low + ((to_find - ((Product)list.get(low).data()).code()) * (high - low)) / (((Product)list.get(high).data()).code() - ((Product)list.get(low).data()).code());

            if (((Product)list.get(mid).data()).code() < to_find)
                low = mid + 1;
            else if (((Product)list.get(mid).data()).code() > to_find)
                high = mid - 1;
            else
                return mid;
        }
        if (((Product)list.get(low).data()).code() == to_find)
            return low;
        else
            return -1;
    }
    
    /**
     * Método para buscar por interpolación en forma descendente.
     * @param to_find el elemento por buscar.
     * @param list la lista ordenada.
     * @return la posición del elemento dentro de la lista.
     */
    private static int decreased_interpolation_search(List<Product> list, int to_find){

        int low = 0;
        int high = list.size() - 1;
        int mid;
        while (((Product)list.get(low).data()).code() >= to_find && ((Product)list.get(high).data()).code() <= to_find)
        {
            if ((((Product)list.get(high).data()).code() - ((Product)list.get(low).data()).code()) == 0)
                return (low + high)/2;

            mid = low + ((to_find - ((Product)list.get(low).data()).code()) * (high - low)) / (((Product)list.get(high).data()).code() - ((Product)list.get(low).data()).code());

            if (((Product)list.get(mid).data()).code() > to_find)
                low = mid + 1;
            else if (((Product)list.get(mid).data()).code() < to_find)
                high = mid - 1;
            else
                return mid;
        }
        if (((Product)list.get(low).data()).code() == to_find)
            return low;
        else
            return -1;
    }
    
    
}
