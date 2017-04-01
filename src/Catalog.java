import java.util.*;

/**
 * contains all caravans in the system
 *
 * @author  Kevin Ni
 * @since   2017-03-30
 */
public class Catalog{

	List<Caravan> allVans;
	Map<String, Depot> allDepots;
	Map<String, Order> allOrders;

	public Catalog(){
		allVans = new ArrayList<Caravan>();
		allDepots = new HashMap<String, Depot>();
		allOrders = new HashMap<String, Order>();
	}

	public void addVan(String depotName, String vanName, boolean isAuto){
		Depot depot = allDepots.putIfAbsent(depotName, new Depot(depotName));
		Caravan van = new Caravan(vanName, depot, allDepots.size(), isAuto); 
		allVans.add(van);
	}

	public void makeOrder(String id, Interval interval, int autos, int manuals){
		Order order = new Order(interval, autos, manuals);

		if(order.tryGetBooking(allVans)){
			allOrders.put(id, order);
		}
	}
}
