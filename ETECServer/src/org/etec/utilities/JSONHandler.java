package org.etec.utilities;

import org.etec.datastructures.Edge;
import org.etec.datastructures.List;
import org.etec.datastructures.Node;
import org.etec.management.DistributionCenter;
import org.etec.management.Messenger;
import org.etec.management.Package;
import org.etec.management.Product;
import org.etec.management.ShoppingCart;
import org.etec.management.ShoppingCart.ShoppingProduct;
import org.etec.management.User;
import org.json.JSONArray;
import org.json.JSONObject;

public class JSONHandler {
	
	/**
	 * Parsea información en string-json a objeto-json.
	 * @param info la información en string.
	 * @return el objeto json.
	 */
	public static JSONObject parse(String info){
		return new JSONObject(info);
	}
	
	/**
	 * Construye un json con los centros de distribución disponibles.
	 * @param centers la lista de centros.
	 * @return el json con los nombres de los centros.
	 */
	public static String build_centers_list(List<DistributionCenter> centers) {
		
		JSONObject json_centers = new JSONObject();
		JSONArray list = new JSONArray();
		
		for(int i = 0; i < centers.size(); i++){
			list.put(((DistributionCenter)centers.get(i).data()).name());
		}
		json_centers.put("centers", list);
		return json_centers.toString();
	}
	
	/**
	 * Construye un json con la información del usuario.
	 * @param user el usuario.
	 * @return un json con la información del usuario.
	 */
	public static String build_user_info(User user){
		JSONObject user_info = new JSONObject();
		user_info.put("name", user.name());
		user_info.put("email", user.email());
		user_info.put("center", user.center().name());
		return user_info.toString();
	}
	
	/**
	 * Construye una lista en con todos los productos disponibles
	 * @return un json con la lista de productos
	 */
	public static String build_products_list(Product[] electronic, Product[] furniture, Product[] clothes){
		JSONObject products = new JSONObject();
		JSONArray list = new JSONArray();
		for(int i = 0; i < electronic.length; i++){
			list.put(electronic[i].name());
		}
		for(int i = 0; i < furniture.length; i++){
			list.put(furniture[i].name());
		}
		for(int i = 0; i < clothes.length; i++){
			list.put(clothes[i].name());
		}
		products.put("products", list);
		return products.toString();
	}
	
	/**
	 * Construye un json con las tiendas que tienen el producto deseado.
	 * @return el json con la lista de tiendas.
	 */
	public static String build_search_result(List<String> stores, Product product){
		
		JSONObject result = new JSONObject();
		JSONArray list = new JSONArray();
		
		for(int i = 0; i < stores.size(); i++){
			list.put(stores.get(i).data());
		}
		result.put("stores", list);
		result.put("product", product.name());
		result.put("cost", product.cost());
		return result.toString();
	}
	
	/**
	 * Construye un json con la información del carrito de compras.
	 * @param cart el carrito de compras.
	 * @return el json con la información.
	 */
	public static String build_shopping_cart(ShoppingCart cart){
		
		JSONObject json_cart = new JSONObject();
		JSONArray products_array = new JSONArray();
		JSONArray quantities_array = new JSONArray();
		
		List<ShoppingProduct> list = cart.products_list();
		try{
			for(int i = 0; i < list.size(); i++){
				ShoppingProduct current = (ShoppingProduct)list.get(i).data();
				products_array.put(current.product().name());
				quantities_array.put(current.quantity());
			}
			json_cart.put("products", products_array);
			json_cart.put("quantities", quantities_array);
			json_cart.put("total", cart.total());
		}catch (Exception e) {
			json_cart.put("products", products_array);
			json_cart.put("quantities", quantities_array);
			json_cart.put("total", "NO HAY PRODUCTOS");
		}
		return json_cart.toString();
	}
	
	/**
	 * Construye la información del paquete.
	 * @return la información en json.
	 */
	public static String build_package_info(Messenger m){
		
		JSONObject info = new JSONObject();
		JSONArray list = new JSONArray();
		
		for(int i = 0; i < m.delivery().content().size(); i++){
			ShoppingProduct current = (ShoppingProduct)m.delivery().content().get(i).data();
			list.put(current.product().name());
		}
		
		info.put("date", m.delivery().date());
		info.put("status", m.delivery().status());
		info.put("products", list);
		info.put("current", m.current_point().name());
		
		return info.toString();
	}
	
	/**
	 * Construye un json con la información de los paquetes en tránsito.
	 * @param active la lista con todos los paquetes activos de todos los centros.
	 * @return el json con la información de los paquetes pendientes.
	 */
	public static String build_active_packages(List<List<Package>> active){
		
		JSONObject json_active = new JSONObject();
		JSONArray array = new JSONArray();
		
		for(int i = 0; i < active.size(); i++){
			List<Package> current_list = (List<Package>)active.get(i).data();
			for(int j = 0; j < current_list.size(); j++){
				if (!current_list.isEmpty()) {
					Package current_package = (Package)current_list.get(j).data();
					array.put(current_package.messenger().current_point().name());
				}
			}
			json_active.put("packages", array);
		}
		return json_active.toString();
	}
	
	/**
	 * Construye un mensaje con el resultado de la petición del cierre de ruta.
	 * @param result si es posible cerrarla o no.
	 * @param from el punto de inicio.
	 * @param to el destino.
	 * @return un json con la información.
	 */
	public static String build_result_message(boolean result, String from, String to){
		JSONObject result_info = new JSONObject();
		if (result) {
			result_info.put("from", from);
			result_info.put("to", to);
			result_info.put("result", result);
		}else{
			result_info.put("result", result);
		}
		return result_info.toString();		
	}
	
	/**
	 * Construye un json con la información de un cambio en las rutas.
	 * @param path_closed el camino cerrado.
	 * @return la información del camino en json.
	 */
	public static String build_route_change(Edge path_closed){
		JSONObject info = new JSONObject();
		try {
			info.put("from", path_closed.from().label());
			info.put("to", path_closed.to().label());
			info.put("result", true);
		} catch (NullPointerException e) {
			info.put("result", false);
		}
		return info.toString();
	}
	
	/**
	 * Construye un json con los paquetes dentro de cada centro de distribución disponible.
	 * @param el centro a ser buscado.
	 * @return el json con los paquetes dentro de un centro.
	 */
	public static String build_centers_package_list(DistributionCenter center) {
		JSONObject json_center_pkgs = new JSONObject();
		JSONArray deliveredList = new JSONArray();
		for(int i = 0; i < center.delivered().size(); i++){
			Package current = (Package) center.delivered().get(i).data();
			deliveredList.put(current.id());
			}
		json_center_pkgs.put("delivered", deliveredList);
		JSONArray pendingList = new JSONArray();
		for(int i = 0; i < center.pending().size(); i++){
			Package current = (Package) center.pending().get(i).data();
			pendingList.put(current.id());
		}
		json_center_pkgs.put("pending", pendingList);
		return json_center_pkgs.toString();
	}
	
	/**
	 * Construye un Json con los estados del paquete
	 * @param un estado booleano de si se ha entregado
	 * @return un Json con el estado actual del paquete
	 */
	public static String build_package_stat(Boolean isDelivered) {
		JSONObject json_center_pkgs = new JSONObject();
		json_center_pkgs.put("stat",isDelivered);
		return json_center_pkgs.toString();
	}
}
