package org.etec.management;

import java.util.Random;

import org.etec.datastructures.List;
import org.etec.searchalgos.Finder;

public class Store extends Establishment{
	
	private List<Product> products_list;
	
	public Store(String type, String name, String category, int id) {
		super(type,name,category,id);
		products_list = new List<>();	
		set_inventory();
	}
	
	/**
	 * Inicializa el inventario de la tienda.
	 * @param products la lista de productos
	 */
	public void set_inventory(){
		Product[] products = null;
		if (category().equals("electronics")) {
			products = NetworkManager.electronic_products;
		}else if (category().equals("furniture")) {
			products = NetworkManager.furniture_products;
		}else{
			products = NetworkManager.clothing_products;
		}
		Random random = new Random();
		
		for(int i = 0; i < products.length; i++){
			products_list.addLast(products[random.nextInt(products.length)]);
		}
	}
	
	/**
	 * Busca un producto en el inventario de la tienda.
	 * @param name el nombre del producto.
	 * @return el objeto con el producto.
	 */
	public Product find_product(String name){
		for(int i = 0; i < products_list.size(); i++){
			Product current = (Product)products_list.get(i).data();
			if (current.name().equals(name)) {
				return current;
			}
		}
		return null;
	}
	
	/**
	 * @return el inventario de la tienda.
	 */
	public List<Product> inventory() {
		return this.products_list;
	}
}
