package org.etec.searchalgos;

import org.etec.datastructures.List;
import org.etec.datastructures.Node;
import org.etec.management.DistributionCenter;
import org.etec.management.Product;
import org.etec.management.Store;
import org.etec.management.User;
import org.etec.management.Package;

public class Finder {
	
	/**
	 * Busca un objeto con las condiciones dadas.
	 * @param algo el algoritmo de busqueda.
	 * @param order el orden de ordenamiento de los elementos.
	 * @param name el nombre del producto.
	 * @param list la lista donde se busca.
	 * @return el objeto con el producto.
	 */
	public static Product find_product(String algo, String order, int to_find, List<Product> list){
		switch(algo){
			case "Binary Search":
				return (Product)list.get(BinarySearch.search(list, to_find, order)).data();
			case "Interpolation Search":
				return (Product)list.get(InterpolationSearch.search(list, to_find, order)).data();
		}
		return null;
	}
    
    /**
     * Busca un centro de distribución.
     * @param name el nombre del centro.
     * @param centers al lista de centros.
     * @return el objeto con el centro de distribución.
     */
    public static DistributionCenter find_center(String name, List<DistributionCenter> centers){
    	for(int k = 0; k < centers.size(); k++){
    		if (((DistributionCenter)centers.get(k).data()).name().equals(name)) {
				return (DistributionCenter)centers.get(k).data();
			}
    	}
    	return null;
    }
    
    /**
     * Busca un usuario.
     * @param name el nombre del usuario.
     * @param users la lista de usuarios.
     * @return el objeto con el usuario.
     */
    @SuppressWarnings("unchecked")
    public static User find_user(String name, List<User> users){
    	Node<User> current = users.peek();
    	while(current != null){
    		if (current.data().name().trim().equals(name.trim())) {
				return current.data();
			}
    		current = current.next();
    	}
    	return null;
    }
    
    /**
     * Busca una tienda por nombre.
     * @param name el nombre de la tienda.
     * @param stores la lista de tiendas.
     * @return el objeto con la tienda.
     */
    public static Store find_store(String name, List<Store> stores){
    	for(int i = 0; i < stores.size(); i++){
    		Store current = (Store)stores.get(i).data();
    		if (current.name().equals(name)) {
				return current;
			}
    	}
    	return null;
    }
    
    /**
     * Busca un paquete por su ID dentro de un centro de distribucion
     * @param id id del paquete
     * @param center el centro en que se va a buscar
     * @return el paquete
     */
    public static Package find_package(int id, DistributionCenter center){
    	
    	for(int i = 0; i<center.pending().size();i++){
    		 Package current = (Package)center.pending().get(i).data();
    		if(current.id() == id) return current;
    	}
    	return null;
    }
}
