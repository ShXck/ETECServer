package org.etec.sortingalgos;

import org.etec.datastructures.List;
import org.etec.management.Product;

public class ShellSort {
	
	/**
	 * Función que ordena la lista de acuerdo a las especificaciones dadas.
	 * @param order si es descendente o ascendente.
	 * @param attr el atributo por el cual se va a ordenar.
	 * @return la lista ordenada
	 */
	public static List<Product> sort(String order, List<Product> list){
		switch(order){
			case "Ascendente":
				return increasing_code_shell_sort(list);
			case "Descendente":
				return decreasing_code_shell_sort(list);			
		}
		return list;
	}
	
	/**
	 * Ordena los productos por precio ascendentemente.
	 * @param list la lista de productos.
	 * @return la lista ordenada.
	 */
	private static List<Product> increasing_code_shell_sort(List<Product> list){

        for (int k = list.size()/2; k != 0; k /= 2){
            boolean changes = true;
            while (changes){
                changes = false;
                for (int i = k; i < list.size(); i++){
                    if (((Product)list.get(i-k).data()).code() > ((Product) list.get(i).data()).code()){
                        Product aux = (Product) list.get(i).data();
                        list.get(i).setData(list.get(i-k).data());
                        list.get(i-k).setData(aux);
                        changes = true;
                    }
                }
            }
        }
        return list;
    }
	
	
	/**
	 * Ordena los productos por precio en forma descendente.
	 * @param list la lista de productos.
	 * @return la lista ordenada.
	 */
	private static List<Product> decreasing_code_shell_sort(List<Product> list){

		for (int k = list.size() / 2; k != 0; k /= 2) {
            boolean changes = true;
            while (changes) {
                changes = false;
                for (int i = k; i < list.size(); i++) {
                    if (((Product) list.get(i - k).data()).code() < ((Product) list.get(i).data()).code()) {
                        Product aux = (Product) list.get(i).data();
                        list.get(i).setData(list.get(i - k).data());
                        list.get(i - k).setData(aux);
                        changes = true;
                    }
                }
            }
        }
        return list;
	}
}
