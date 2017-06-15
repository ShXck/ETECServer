package org.etec.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.etec.datastructures.List;
import org.etec.management.Messenger;
import org.etec.management.NetworkManager;
import org.etec.management.Package;
import org.etec.management.Product;
import org.etec.management.Store;
import org.etec.management.StoreManager;
import org.etec.management.User;
import org.etec.searchalgos.Finder;
import org.etec.utilities.JSONHandler;
import org.etec.utilities.Mailer;
import org.json.JSONObject;

@Path("/user")
public class ClientResources {
	
	public static NetworkManager manager = new NetworkManager();
	private static StoreManager store_manager = new StoreManager();
	private static List<User> all_users = new List<>();
	
	@POST
	@Path("/log")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response register_user(String user_info){
		JSONObject o = JSONHandler.parse(user_info);
		try{
			String name = Finder.find_user(o.getString("name"), all_users).name();
		}catch (NullPointerException e) {
			User new_user = new User(o.getString("name"), o.getString("email"), Finder.find_center(o.getString("center"), manager.centers()));
			all_users.addLast(new_user);
			return Response.ok("Welcome to ETEC!").build();
		}
		return Response.ok("Welcome Back to ETEC").build();
	}
	
	@GET
	@Path("/centers")
	@Produces(MediaType.APPLICATION_JSON)
	public Response get_all_centers(){
		return Response.ok(JSONHandler.build_centers_list(manager.centers())).build();
	}
	
	@GET
	@Path("{name}/info")
	@Produces(MediaType.APPLICATION_JSON)
	public Response get_user_info(@PathParam("name") String user_name){
		return Response.ok(JSONHandler.build_user_info(Finder.find_user(user_name, all_users))).build();
	}
	
	@GET
	@Path("/check/{name}")
	public Response check_account(@PathParam("name") String name) {
		try {
			String user = Finder.find_user(name, all_users).name();
		} catch (Exception e) {
			return Response.ok("{status:notfound}").build();
		}
		return Response.ok("{status:found}").build();
	}
	
	@GET
	@Path("/products")
	@Produces(MediaType.APPLICATION_JSON)
	public Response get_all_products(){
		return Response.ok(manager.all_products()).build();
	}
	
	@POST
	@Path("/search")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response search_product(String specs){
		JSONObject json_specs = new JSONObject(specs);
		int code = store_manager.find_product_code(json_specs.getString("product"));
		String result = store_manager.find(manager.stores(), json_specs.getString("search"), json_specs.getString("sort"), json_specs.getString("order"), code);
		return Response.ok(result).build();
	}
	
	@POST
	@Path("{user}/add")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response add_item_to_cart(@PathParam("user") String user_name, String product_info){
		JSONObject details = new JSONObject(product_info);
		User user = Finder.find_user(user_name, all_users);
		Store store = Finder.find_store(details.getString("store"), manager.stores());
		Product product = store.find_product(details.getString("product"));
		user.cart().add_item(product, store, details.getInt("quantity"));
		return Response.ok(manager.all_products()).build();
	}
	
	@GET
	@Path("/{user}/cart")
	public Response get_cart(@PathParam("user") String name){
		User user = Finder.find_user(name, all_users);
		return Response.ok(JSONHandler.build_shopping_cart(user.cart())).build();
	}
	
	@PUT
	@Path("/{user}/cart")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response modify_item(@PathParam("user") String user, String data){
		JSONObject info = new JSONObject(data);
		User u = Finder.find_user(user, all_users);
		u.cart().modify_quantity(info.getString("product"), info.getInt("quantity"));
		return Response.ok(JSONHandler.build_shopping_cart(u.cart())).build();
	}
	
	@POST
	@Path("/{user}/buy")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response buy(@PathParam("user") String name){
		User user = Finder.find_user(name, all_users);
		Package order = new Package(user);
		Messenger messenger = new Messenger(user.center(), order);
		order.set_messenger(messenger);
		user.set_order(order);
		manager.run_order(messenger,manager,user.center());
		user.center().add_pending_package(order);
		user.cart().restart();
		Mailer.send_purchase_msg(user.email(), order);
		return Response.ok(JSONHandler.build_package_info(messenger)).build();
	}
	
	@GET
	@Path("/{user}/order")
	@Produces(MediaType.APPLICATION_JSON)
	public Response get_order_details(@PathParam("user") String user){
		User u = Finder.find_user(user, all_users);
		return Response.ok(JSONHandler.build_package_info(u.order().messenger())).build();
	}
	
	
	@POST
	@Path("/create")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response add_new_establishment(String details){
		JSONObject json = new JSONObject(details);
		manager.add_stablishment(json.getString("type"), json.getString("name"), json.getString("category"), 
				json.getString("in"), json.getString("out"), json.getString("distance"), json.getString("time"), json.getString("danger"));
		return Response.ok("{}").build();
	}
	
	@GET
	@Path("/packages/active")
	public Response get_packages_info(){
		return Response.ok(JSONHandler.build_active_packages(manager.get_active_packages())).build();
	}
	
	@DELETE
	@Path("/paths")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response close_path(String data){
		JSONObject details = new JSONObject(data);
		boolean result = manager.can_close(details.getString("from"), details.getString("to"));
		if (result) {
			manager.change_routes();
		}
		return Response.ok(JSONHandler.build_result_message(result, details.getString("from"), details.getString("to"))).build();
	}
	
	@GET
	@Path("/paths")
	@Produces(MediaType.APPLICATION_JSON)
	public Response get_path_status(){
		return Response.ok(JSONHandler.build_route_change(manager.route_changes())).build();
	}
}
