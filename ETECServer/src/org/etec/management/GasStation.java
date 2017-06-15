package org.etec.management;

import java.util.Random;


public class GasStation extends Establishment{
	
	Random random = new Random();

	public GasStation(String type, String name) {
		super(type,name);
	}
	
	public GasStation(String type, String name, int id){
		super(type, name,id);
	}
	
	/**
	 * @return El tiempo que se detendrá el paquete en la estación de servicio.
	 */
	public double delay(){
		double min = 1.5;
		double max = 3.0;
		return min + (max - min) * random.nextDouble();
	}

}
