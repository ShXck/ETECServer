package org.etec.management;
import org.etec.datastructures.List;
import org.etec.datastructures.Node;

public class DistributionCenter extends Establishment{
	
	private List<Package> delivered_packages;
	private List<Package> pending_packages;
	
	public DistributionCenter(String type, String name, int id) {
		super(type,name,id);
		this.delivered_packages = new List<>();
		this.pending_packages = new List<>();
	}
	
	
	/**
	 * Agrega paquetes no entregados a la lista de pendientes.
	 * @param pending el paquete pendiente.
	 */
	public void add_pending_package(Package pending){
		pending_packages.addLast(pending);
	}
	
	/**
	 * Agrega paquetes que han llegado al centro.
	 * @param delivered el paquete listo.
	 */
	public void add_delivered(Package delivered){
		delivered_packages.addLast(delivered);
		pending_packages.remove(delivered);
	}
	
	/**
	 * @return los paquetes pendientes con destino al centro respectivo.
	 */
	public List<Package> pending(){
		return this.pending_packages;
	}
	
	/**
	 * @return los paquetes entregados al centro de distribución.
	 */
	public List<Package> delivered() {
		return this.delivered_packages;
	}
}
