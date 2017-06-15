package org.etec.management;

import org.etec.datastructures.List;
import org.etec.searchalgos.Finder;
import org.etec.sortingalgos.BubbleSort;
import org.etec.sortingalgos.InsertionSort;
import org.etec.sortingalgos.MergeSort;
import org.etec.sortingalgos.Quicksort;
import org.etec.sortingalgos.SelectionSort;
import org.etec.sortingalgos.ShellSort;
import org.etec.utilities.JSONHandler;

public class StoreManager {
	
	public StoreManager(){
	}
	
	/**
	 * Busca un producto en todas las tiendas.
	 * @param search_algo el algoritmo de búsqueda.
	 * @param sort_algo el algoritmo de ordenamiento.
	 * @param attribute el atributo para ordeanar.
	 * @param order el orden de ordenamiento.
	 */
	public String find(List<Store> stores, String search_algo, String sort_algo, String order, int to_find){
		
		List<String> stores_names = new List<>();
		Product p = null; 
		
		for(int i = 0; i < stores.size(); i++){
			Store current = (Store)stores.get(i).data();
			List<Product> products = sort_products(sort_algo, order, current.inventory());
			try{
				p = Finder.find_product(search_algo, order, to_find, products);
				stores_names.addLast(current.name());
			}catch (IndexOutOfBoundsException e) {
				//
			}
		}
		return JSONHandler.build_search_result(stores_names, p);
	}
	
	/**
	 * Encuentra el código de un producto.
	 * @param name el nombre del producto.
	 * @return el código del producto.
	 */
	public int find_product_code(String name){
		List<Product> a = NetworkManager.all;
		
		for(int w = 0; w < a.size(); w++){
			if (((Product)a.get(w).data()).name().equals(name)) {
				return ((Product)a.get(w).data()).code();
			}
		}
		return -1;
	}
	
	/**
	 * Encuentra una tienda.
	 * @param name el nombre de la tienda.
	 * @return el objeto con la tienda.
	 */
	public Store find_store(String name){
		List<Store> stores = NetworkManager.stores;
		for(int i = 0; i < stores.size(); i++){
			Store current = (Store)stores.get(i).data();
			if (current.name().equals(name)) {
				return current;
			}
		}
		return null;
	}
	
	
	/**
	 * Ordena la lista de inventario de cada tienda.
	 * @param algo el algoritmo de ordenamiento.
	 * @param attribute el atributo para ser ordenados.
	 * @param order el orden de ordenamiento.
	 * @param list la lista desordenada.
	 */
	private List<Product> sort_products(String algo, String order, List<Product> list){
		switch(algo){
			case "Bubble Sort":
				return BubbleSort.sort(order, list);
			case "Insertion Sort":
				return InsertionSort.sort(order, list);
			case "Merge Sort":
				return MergeSort.sort(order, list);
			case "Quick Sort":
				return Quicksort.sort(order, list);
			case "Selection Sort":
				return SelectionSort.sort(order, list);
			case "Shell Sort":
				return ShellSort.sort(order, list);
		}
		return list;
	}
}
