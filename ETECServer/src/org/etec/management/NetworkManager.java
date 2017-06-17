package org.etec.management;
import org.etec.datastructures.Dijsktra;
import org.etec.datastructures.Edge;
import org.etec.datastructures.Graph;
import org.etec.datastructures.List;
import org.etec.datastructures.Node;
import org.etec.datastructures.TransitiveClosure;
import org.etec.datastructures.Vertex;
import org.etec.utilities.RandomIntegerGenerator;
import org.etec.utilities.JSONHandler;
import org.etec.utilities.XMLHandler;

public class NetworkManager {
	
	private Graph<Establishment> network;
	private List<GasStation> stations;
	private List<DistributionCenter> centers;
	public static List<Store> stores;
	public static Product[] electronic_products;
	public static Product[] clothing_products;
	public static Product[] furniture_products;
	public static List<Product> all;
	public static String all_products;
	private Edge last_route_change;
	private List<OrderRunner> active_packages;
	
	public NetworkManager(){
		network = new Graph<>();
		stations = new List<>();
		centers = new List<>();
		stores = new List<>();
		electronic_products = new Product[20];
		furniture_products = new Product[20];
		clothing_products = new Product[20];
		all = new List<Product>();
		active_packages = new List<>();
		last_route_change = null;
		load_data();
		
	}
	
	/**
	 * Añade un establecimiento al grafo.
	 * @param type el tipo de establecimiento.
	 * @param name el nombre del establecimiento.
	 * @param category la categoria (aplica para tiendas solamente)
	 * @param in las conexiones entrantes del vértice.
	 * @param out las conexiones salientes del vértice.
	 */
	public void add_stablishment(String type, String name, String category, String in, String out, String distance, String time, String danger){
		switch(type){
			case "Center":
				DistributionCenter new_center = new DistributionCenter(type, name);
				network.add_vertex(new_center, new_center.name());
				Vertex<Establishment> vertex = network.find_vertex(name);
				new_center.set_id(vertex.id());
				set_connections(name, in, out, distance, time, danger);
				centers.addLast(new_center);
				break;
			case "Store":
				Store new_store = new Store(type,name,category);
				stores.addLast(new_store);
				network.add_vertex(new_store, new_store.name());
				Vertex<Establishment> v = network.find_vertex(name);
				new_store.set_id(v.id());
				set_connections(name, in, out, distance, time, danger);
				break;
			case "Station":
				GasStation new_station = new GasStation(type,name);
				network.add_vertex(new_station, new_station.name());
				Vertex<Establishment> vertex_s = network.find_vertex(name);
				new_station.set_id(vertex_s.id());
				set_connections(name, in, out, distance, time, danger);
				stations.addLast(new_station);
				break;
		}
	}
	
	/**
	 * Establece las conexiones del nuevo establecimiento.
	 * @param name el nombre del establecimiento nuevo.
	 * @param in las conexiones entrantes;
	 * @param out las conexiones salientes.
	 */
	private void set_connections(String name, String in, String out, String distance, String time, String danger){
		
		String[] in_result = in.split(",");
		String[] out_result = out.split(",");
		int[] distances_result = RandomIntegerGenerator.to_int(distance.split(","));
		int[] time_result = RandomIntegerGenerator.to_int(time.split(","));
		int[] danger_result = RandomIntegerGenerator.to_int(danger.split(","));
		
		Vertex<Establishment> vertex = network.find_vertex(name);
		
		for(int i = 0; i < out_result.length; i++){
			Vertex<Establishment> current_out = network.find_vertex(out_result[i]);
			network.add_edge(vertex, current_out, distances_result[i] + time_result[i] + danger_result[i]);
		}
		
		for(int j = distances_result.length - in_result.length; j < distances_result.length; j++){
			int k = in_result.length;
			if (k > j) {
				Vertex<Establishment> current_in = network.find_vertex(in_result[(j+1)-k]);
				network.add_edge(current_in, vertex, distances_result[j] + time_result[j] + danger_result[j]);
			}else if (k == j) {
				Vertex<Establishment> current_in = network.find_vertex(in_result[j-k]);
				network.add_edge(current_in, vertex, distances_result[j] + time_result[j] + danger_result[j]);
			}else{
				Vertex<Establishment> current_in = network.find_vertex(in_result[(j-1)-k]);
				network.add_edge(current_in, vertex, distances_result[j] + time_result[j] + danger_result[j]);
			}			
		}
	}
	
	/**
	 * Obtiene las paquetes en tránsito de la red.
	 */
	public List<List<Package>> get_active_packages(){
		List<List<Package>> active_packages = new List<>();
		for(int i = 0; i < centers.size(); i++){
			DistributionCenter current = (DistributionCenter)centers.get(i).data();
			if (!current.pending().isEmpty()) active_packages.addLast(current.pending());
		}
		return active_packages;
	}
	
	/**
	 * Inicia el desplazamiento del paquete.
	 * @param messenger el mensajero.
	 * @param manager el gestor de tiendas.
	 */
	public void run_order(Messenger messenger, NetworkManager manager, DistributionCenter center){
		OrderRunner new_order = new OrderRunner(manager,messenger,center);
		new_order.start();
		active_packages.addLast(new_order);
	}
	
	/**
	 * Determina si se puede cerrar una ruta o no.
	 * @param from el punto de inicio.
	 * @param to el destino de la conexión.
	 * @return si se puede cerrar o no.
	 */
	public boolean can_close(String from, String to){
		Vertex<Establishment> from_vertex = network.find_vertex(from);
		Vertex<Establishment> to_vertex = network.find_vertex(to);
		Edge temp = new Edge(network.find_edge(from_vertex, to_vertex).weight(), from_vertex, to_vertex);
		
		Dijsktra dijsktra = new Dijsktra();
		
		network.remove_edge(network.find_edge(from_vertex, to_vertex));
		
		try{
			dijsktra.dijkstra(network, 0);
		}catch (IndexOutOfBoundsException e) {
			network.add_edge(from_vertex, to_vertex, temp.weight());
			return false;
		}
		
		last_route_change = temp;
		
		return true;
	}
	
	/**
	 * Recorre la lista que contiene los paquetes que estan en desplazamiento y asigna una nueva ruta.
	 */
	public void change_routes(){
		for(int i = 0; i < active_packages.size(); i++){
			OrderRunner current = (OrderRunner)active_packages.get(i).data();
			current.change_route();
		}
	}
	
	/**
	 * Conecta dos establecimientos.
	 * @param from la fuente.
	 * @param to el destino.
	 * @param weight el peso de la relación.
	 */
	public void connect(Establishment from, Establishment to, int weight){
		network.add_edge(find_stablishment(from), find_stablishment(to), weight);
	}
	
	/**
	 * Encuentra el vértice que contiene un establecimiento.
	 * @param s el establecimiento
	 * @return el vertice que contiene al establecimiento.
	 */
	private Vertex<Establishment> find_stablishment(Establishment s){
		
		Node<Vertex<Establishment>> current = network.vertexes().peek();
		
		while(current != null){
			if(current.data().data().equals(s)){
				return current.data();
			}
			current = current.next();
		}
		return null;
	}
	
	/**
	 * Encuentra un establecimiento.
	 * @param s el establecimiento
	 * @return el vertice que contiene al establecimiento.
	 */
	public Establishment find_point(String name){
		for(int i = 0; i < network.vertexes().size(); i++){
			Establishment current = ((Vertex<Establishment>)network.vertexes().get(i).data()).data();
			if (current.name().equals(name)) {
				return current;
			}
		}
		return null;
	}
	
	/**
	 * Carga la información inicial del sistema.
	 */
	private void load_data(){
		XMLHandler.load_clothing_products(clothing_products,all);
		XMLHandler.load_electronic_products(electronic_products,all);
		XMLHandler.load_furniture_products(furniture_products,all);
		this.all_products = JSONHandler.build_products_list(electronic_products, furniture_products, clothing_products);
		build_graph();
	}
	
	/**
	 * @return la lista de ordenes en desplazamiento.
	 */
	public List<OrderRunner> running_orders(){
		return this.active_packages;
	}
	
	/**
	 * @return el grafo que representa la red.
	 */
	public Graph<Establishment> graph(){
		return this.network;
	}
	
	
	/**
	 * @return la lista de centros de distribución.
	 */
	public List<DistributionCenter> centers() {
		return this.centers;
	}
	
	/**
	 * @return la lista de tiendas.
	 */
	public List<Store> stores() {
		return stores;
	}
	
	/**
	 * @return la lista completa de productos.
	 */
	public String all_products() {
		return all_products;
	}
	
	/**
	 * @return el ultimo cambio en las rutas.
	 */
	public Edge route_changes(){
		return this.last_route_change;
	}
	
	/**
	 * Limpia el último cambio de los caminos.
	 */
	public void clear_route_changes(){
		this.last_route_change = null;
	}
	
	/**
	 * Carga el grafo inicial
	 */
	public void build_graph(){
		
		String e = "electronics";
		String f = "furniture";
		String c = "clothes";
		
		DistributionCenter azalea = new DistributionCenter("center","Azalea", 0);
        Store ar = new Store("store","A+R",f, 1);
        Store hgx = new Store("store","HGX",e, 2);
        GasStation exxon = new GasStation("station","Exxon", 3);
        Store axxes = new Store("store","AXXES",e, 4);
        DistributionCenter venice = new DistributionCenter("center","Venice", 5);
        Store jack_spade = new Store("store","Jack Spade",c, 6);
        Store weego = new Store("store","WeegoHome",f, 7);
        Store nxt = new Store("store","NXT",e, 8);
        DistributionCenter krystal = new DistributionCenter("center","Krystal", 9);
        GasStation shell = new GasStation("station","Shell", 10);
        Store jenni_kayne = new Store("store","Jenni Kayne",c, 11);
        GasStation texaco = new GasStation("station","Texaco", 12);
        DistributionCenter dahlia = new DistributionCenter("center","Dahlia", 13);
        
        centers.addLast(azalea); centers.addLast(venice);centers.addLast(krystal);centers.addLast(dahlia);
        stores.addLast(ar);stores.addLast(hgx);stores.addLast(axxes);stores.addLast(jack_spade);stores.addLast(weego);stores.addLast(nxt);
        stores.addLast(jenni_kayne); stations.addLast(exxon);stations.addLast(shell);stations.addLast(texaco);

        Vertex<Establishment> azal = new Vertex<>(azalea, azalea.name(), azalea.id());
        Vertex<Establishment> arv = new Vertex<>(ar, ar.name(), ar.id());
        Vertex<Establishment> exx = new Vertex<>(exxon, exxon.name(), exxon.id());
        Vertex<Establishment> hgxv = new Vertex<>(hgx, hgx.name(), hgx.id());
        Vertex<Establishment> axx = new Vertex<>(axxes, axxes.name(), axxes.id());
        Vertex<Establishment> venic = new Vertex<>(venice, venice.name(), venice.id());
        Vertex<Establishment> jackspade = new Vertex<>(jack_spade, jack_spade.name(), jack_spade.id());
        Vertex<Establishment> weegov = new Vertex<>(weego, weego.name(), weego.id());
        Vertex<Establishment> nxtv = new Vertex<>(nxt, nxt.name(), nxt.id());
        Vertex<Establishment> kryst = new Vertex<>(krystal, krystal.name(), krystal.id());
        Vertex<Establishment> shel = new Vertex<>(shell, shell.name(), shell.id());
        Vertex<Establishment> jenni = new Vertex<>(jenni_kayne, jenni_kayne.name(), jenni_kayne.id());
        Vertex<Establishment> tex = new Vertex<>(texaco, texaco.name(), texaco.id());
        Vertex<Establishment> dahl = new Vertex<>(dahlia, dahlia.name(), dahlia.id());

        network.add_vertex(azal);
        network.add_vertex(arv);
        network.add_vertex(exx);
        network.add_vertex(hgxv);
        network.add_vertex(axx);
        network.add_vertex(venic);
        network.add_vertex(jackspade);
        network.add_vertex(weegov);
        network.add_vertex(nxtv);
        network.add_vertex(kryst);
        network.add_vertex(shel);
        network.add_vertex(jenni);
        network.add_vertex(tex);
        network.add_vertex(dahl);
        network.add_edge(azal,arv,15);
        network.add_edge(arv,azal,14);
        network.add_edge(exx,azal,23);
        network.add_edge(exx,arv,10);
        network.add_edge(arv,exx,9);
        network.add_edge(arv,hgxv,8);
        network.add_edge(hgxv,arv,7);
        network.add_edge(hgxv,venic,15);
        network.add_edge(exx,axx,11);
        network.add_edge(axx,venic,7);
        network.add_edge(venic,jackspade,3);
        network.add_edge(venic,nxtv,8);
        network.add_edge(venic,exx,5);
        network.add_edge(jackspade,nxtv,6);
        network.add_edge(axx,weegov,15);
        network.add_edge(weegov,axx,13);
        network.add_edge(jackspade,hgxv,6);
        network.add_edge(jackspade,arv,12);
        network.add_edge(nxtv,weegov,4);
        network.add_edge(weegov,exx,20);
        network.add_edge(nxtv,kryst,4);
        network.add_edge(kryst,jackspade,3);
        network.add_edge(kryst,tex,13);
        network.add_edge(nxtv,jenni,12);
        network.add_edge(jenni,nxtv,11);
        network.add_edge(jenni,tex,11);
        network.add_edge(tex,kryst,7);
        network.add_edge(tex,weegov,16);
        network.add_edge(weegov,shel,10);
        network.add_edge(shel,weegov,13);
        network.add_edge(shel,jenni,9);
        network.add_edge(shel,dahl,7);
        network.add_edge(dahl,jenni,8);
        network.add_edge(dahl,shel,9);
        network.add_edge(tex,dahl,10);		
	}
}
