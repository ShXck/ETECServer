package org.etec.management;

import org.etec.datastructures.List;
import org.etec.datastructures.Node;

public class ShoppingCart {
	
	private List<ShoppingProduct> products_list;
	private int total_cost;
	
	public ShoppingCart(){
		products_list = new List<>();
		total_cost = 0;
	}
	
	/**
	 * Agrega un producto al carrito de compras.
	 * @param item el producto.
	 * @param quantity la cantidad.
	 */
	public void add_item(Product item, Store store, int quantity){
		products_list.addLast(new ShoppingProduct(item, store ,quantity));
		total_cost += quantity * item.cost();
	}
	
	public void modify_quantity(String name, int quantity){
		
		String[] result = name.split("\n"); 
		
		Node<ShoppingProduct> current = products_list.peek();
		
		if (quantity > current.data().quantity) {
			int diff = quantity - current.data().quantity;
			total_cost += diff * current.data().product().cost();
		}else {
			int diff = quantity - current.data().quantity();
			total_cost -= Math.abs(diff * current.data().product().cost());
		}
		
		while (current != null){
			if (current.data().product().name().equals(result[0])) {
				current.data().modify_quantity(quantity);
				break;
			}
			current = current.next();
		}
	}
	
	/**
	 * @return la lista de productos.
	 */
	public List<ShoppingProduct> products_list() {
		return products_list;
	}
	
	/**
	 * @return el costo total de la compra.
	 */
	public int total() {
		return total_cost;
	}
	
	/**
	 * Borra el actual carrito de compras, y crea uno nuevo.
	 */
	public void restart(){
		products_list = new List<>();
		total_cost = 0;
	}
	
	public class ShoppingProduct{
		
		private Product product;
		private int quantity;
		private Store store;
		
		public ShoppingProduct(Product product, Store store, int quantity) {
			this.product = product;
			this.quantity = quantity;
			this.store = store;
		}
		
		/**
		 * @return el producto.
		 */
		public Product product(){
			return this.product;
		}
		
		/**
		 * @return la cantidad seleccionada.
		 */
		public int quantity(){
			return this.quantity;
		}
		
		/**
		 * @return La tienda donde fue comprado el producto.
		 */
		public Store store(){
			return this.store;
		}
		
		/**
		 * Modifica la cantidad del producto.
		 * @param new_quantity la nueva cantidad.
		 */
		public void modify_quantity(int new_quantity){
			this.quantity = new_quantity;
		}
	}
}
