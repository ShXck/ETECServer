package org.etec.management;

import java.util.concurrent.ThreadLocalRandom;
import org.etec.datastructures.List;
import org.etec.management.ShoppingCart.ShoppingProduct;

public class Messenger {
	
	private Package current_package;
	private int fuel;
	private DistributionCenter destination;
	private Store start;
	private Establishment current_point;
	private boolean is_stationary;
	
	public Messenger(DistributionCenter destination, Package p){
		this.destination = destination;
		this.fuel = refuel();
		this.current_package = p;
		this.start = set_starting_point();
		this.current_point = set_starting_point();
		this.is_stationary = false;
	}
	
	/**
	 * Fija un punto de salida para la entrega.
	 * @return la tienda de salida.
	 */
	private Store set_starting_point(){
		List<ShoppingProduct> list = current_package.content();
		return ((ShoppingProduct)list.peek().data()).store();
	}
	
	/**
	 * @return La tienda donde se inicia el camino.
	 */
	public Store starting_point(){
		return this.start;
	}
	
	/**
	 * Fija el nuevo punto del paquete.
	 * @param new_point el nuevo punto
	 */
	public void set_current_point(Establishment new_point){
		this.current_point = new_point;
	}
	
	/**
	 * @return El paquete que debe entregar.
	 */
	public Package delivery(){
		return this.current_package;
	}
	
	/**
	 * @return El centro de destino.
	 */
	public DistributionCenter destination(){
		return this.destination;
	}
	
	/**
	 * @return El punto donde se encuentra la orden.
	 */
	public Establishment current_point(){
		return this.current_point;
	}
	
	/**
	 * @return si el mensajero está en una estación de servicio.
	 */
	public boolean is_stationary(){
		return this.is_stationary;
	}
	
	/**
	 * Fija el valor de is_stationary a true.
	 */
	public void stop() {
		this.is_stationary = true;
	}
	
	/**
	 * Carga un valor aleatorio de gasolina
	 * @return el valor de la gasolina.
	 */
	private int refuel(){
		return ThreadLocalRandom.current().nextInt(1000, 2000 + 1);
	}	

}
