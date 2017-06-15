package org.etec.management;
import java.util.Timer;
import java.util.TimerTask;
import org.etec.datastructures.Dijsktra;
import org.etec.datastructures.Graph;
import org.etec.datastructures.List;
import org.etec.datastructures.Node;
import org.etec.utilities.Mailer;

public class OrderRunner{
	
	private NetworkManager network;
	private Dijsktra dijsktra;
	private Messenger messenger;
	private DistributionCenter destination;
	int[] path;
	private List<String> final_path;
	
	public OrderRunner(NetworkManager manager, Messenger messenger, DistributionCenter center){
		this.network = manager;
		this.dijsktra = new Dijsktra();
		this.messenger = messenger;
		this.destination = center;
		path = dijsktra.dijkstra(manager.graph(), messenger.starting_point().id());
		final_path = dijsktra.find_path_to(network.graph(), messenger.current_point().id(), messenger.destination().id(), path);
	}
	
	/**
	 * Inicia el desplazamiento del paquete.
	 */
	public void start() {
		final_path.printList();
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				Establishment current = network.find_point(((String)final_path.peek().data()));
				if (!current.name().equals(messenger.destination().name())) {
					System.out.println("Current point: " + current.name());
					messenger.set_current_point(current);
					final_path.removeHead();
				}else {
					System.out.println("Current point: " + current.name());
					messenger.set_current_point(current);
					messenger.delivery().change_status("Entregado");
					destination.add_delivered(messenger.delivery());
					Mailer.send_arrived_msg(messenger.delivery().buyer().email(), messenger.delivery());
					timer.cancel();
				}
			}
		},1000, 30000);		
	}
	
	/**
	 * Cambia la ruta de los mensajeros, se da cuando hay un cierre de ruta
	 */
	public void change_route(){
		path = dijsktra.dijkstra(network.graph(), messenger.current_point().id());
		final_path = dijsktra.find_path_to(network.graph(), messenger.current_point().id(), messenger.destination().id(), path);
		
		final_path.printList();
	}
}
