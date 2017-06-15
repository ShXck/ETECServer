package org.etec.management;

public class Product {
	
	private String name;
	private int cost;
	private int code;
	
	public Product(String name, int cost, int code){
		this.name = name;
		this.cost = cost;
		this.code = code;
	}	
	
	/**
	 * @return El nombre del producto.
	 */
	public String name() {
		return name;
	}
	
	/**
	 * @return El precio.
	 */
	public int cost() {
		return cost;
	}
	
	/**
	 * @return el código del producto.
	 */
	public int code(){
		return this.code;
	}
}
