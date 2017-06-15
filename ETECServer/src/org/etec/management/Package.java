package org.etec.management;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.GregorianCalendar;
import org.etec.datastructures.List;
import org.etec.management.ShoppingCart.ShoppingProduct;
import org.etec.utilities.RandomIntegerGenerator;

public class Package {
	
	private List<ShoppingProduct> package_products;
	private User buyer;
	private GregorianCalendar delivery_date;
	private String status;
	private final String DEFAULT_STATUS = "En tránsito";
	private Messenger messenger;
	private int id;
	
	public Package(User buyer){
		this.package_products = buyer.cart().products_list();
		this.buyer = buyer;
		this.delivery_date = new GregorianCalendar();
		this.status = DEFAULT_STATUS;
		calculate_delivery_date();
		this.messenger = null;
		this.id = RandomIntegerGenerator.generate_new_key(4);
	}
	
	/**
	 * Calcula la fecha de entrega.
	 */
	public void calculate_delivery_date(){
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate localDate = LocalDate.now();
        delivery_date.set(localDate.getYear(), random(localDate.getMonthValue(), localDate.getMonthValue() + 1), random(localDate.getDayOfMonth(), 31));
	}
	
	/**
	 * Cambia el estado actual del paquete.
	 * @param new_status el nuevo estado.
	 */
	public void change_status(String new_status){
		this.status = new_status;
	}
	
	/**
	 * @return La fecha de entrega.
	 */
	public String date(){
		SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
		fmt.setCalendar(delivery_date);
		String date = fmt.format(delivery_date.getTime());
		return date;
	}
	
	/**
	 * @return la lista de productos que contiene el paquete.
	 */
	public List<ShoppingProduct> content(){
		return this.package_products;
	}
	
	/**
	 * @return el estado del paquete.
	 */
	public String status(){
		return this.status;
	}
	
	/**
	 * @return El nombre del comprador.
	 */
	public User buyer(){
		return this.buyer;
	}
	
	/**
	 * @return El mensajero a cargo de este paquete.
	 */
	public Messenger messenger(){
		return this.messenger;
	}
	
	/**
	 * Fija un mensajero para este paquete.
	 */
	public void set_messenger(Messenger messenger){
		this.messenger = messenger;
	}
	
	public int id(){
		return this.id;
	}
	
	/**
	 * Devuelve un número aleatorio.
	 * @param min índice menor.
	 * @param max índice mayor.
	 * @return el número generado
	 */
	private int random(int min, int max){
		return min + (int)Math.round(Math.random() * (max - min));
	}
	
	/**
	 * Arma un string con el contenido del paquete.
	 * @return la lista en string del contenido.
	 */
	private String get_content_list(){
		String result = "";
		for(int i = 0; i < package_products.size(); i++){
			ShoppingProduct current = (ShoppingProduct)package_products.get(i).data();
			result += current.product().name() + ",";
		}
		return result;
	}
	
	
	@Override
	public boolean equals(Object object){
	
		if (object == this) {
			return true;
		}
		
		if (!(object instanceof Package)) {
			return false;
		}
		
		Package p = (Package)object;
		return Integer.compare(p.id(), this.id) == 0;
	}
	
	public String toString(){
		return "ID:" + id + "\n Contenido: " + get_content_list();
	}
}
