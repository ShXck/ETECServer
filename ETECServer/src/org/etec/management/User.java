package org.etec.management;

public class User {
	
	private String name;
	private String email;
	private DistributionCenter center;
	private ShoppingCart cart;
	private Package order;
	
	public User(String name, String email, DistributionCenter center){
		this.name = name;
		this.email = email;
		this.center = center;
		this.cart = new ShoppingCart();	
		this.order = null;
	}
	
	/**
	 * @return el nombre del usuario.
	 */
	public String name(){
		return this.name;
	}
	
	/**
	 * @return el email del usuario.
	 */
	public String email(){
		return this.email;
	}
	
	/**
	 * @return el centro de distribución que escogió.
	 */
	public DistributionCenter center() {
		return this.center;
	}
	
	/**
	 * @return el carrito de compras.
	 */
	public ShoppingCart cart(){
		return this.cart;
	}
	
	/**
	 * Fija el valor de la orden.
	 * @param p el paquete.
	 */
	public void set_order(Package p) {
		this.order = p;
	}
	
	/**
	 * @return el paquete del usuario.
	 */
	public Package order(){
		return this.order;
	}
	
	/**
	 * Reinicia el carrito de compras del usuario.
	 */
	public void reset_cart(){
		this.cart = new ShoppingCart();
	}

}
